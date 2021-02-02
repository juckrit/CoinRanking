package com.example.coinranking.presentation.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coinranking.databinding.FragmentMainBinding
import com.example.coinranking.presentation.di.DI_NAME_MainViewModel
import com.example.coinranking.presentation.helper.PostsLoadStateCoinAdapter
import com.example.coinranking.presentation.helper.PostsLoadStateCoinSearchAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by inject((named(DI_NAME_MainViewModel)))
    private lateinit var coinAdapter: CoinPagingDataAdapter
    private lateinit var coinSearchAdapter: CoinSearchPagingDataAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        initSwipeToRefresh()
        initSearch()
        observer()
    }

    private fun initSearch() {
        binding.input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0?.length == 0) {
                    binding.recyclerview.visibility = View.VISIBLE
                    binding.searchRecyclerview.visibility = View.GONE
                }
            }
        })
        binding.input.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updatedCoinFromInput()
                true
            } else {
                false
            }
        }
        binding.input.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updatedCoinFromInput()
                true
            } else {
                false
            }
        }
    }


    private fun initSwipeToRefresh() {
        binding.swiperefresh.setOnRefreshListener { coinAdapter.refresh() }
    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getCoins()?.collectLatest {
                coinAdapter.submitData(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            coinAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.swiperefresh.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            coinAdapter.loadStateFlow
                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.recyclerview.scrollToPosition(0) }
        }

    }

    private fun setup() {
        viewModel.fetchCoin()
        coinAdapter = CoinPagingDataAdapter(requireContext())
        binding.recyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = coinAdapter
            binding.recyclerview.adapter?.notifyDataSetChanged()
        }


        coinSearchAdapter = CoinSearchPagingDataAdapter(requireContext())

        binding.searchRecyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = coinSearchAdapter
            binding.searchRecyclerview.adapter?.notifyDataSetChanged()
        }
        binding.recyclerview.adapter = coinAdapter.withLoadStateHeaderAndFooter(
            header = PostsLoadStateCoinAdapter(coinAdapter),
            footer = PostsLoadStateCoinAdapter(coinAdapter)
        )
        binding.searchRecyclerview.adapter = coinSearchAdapter.withLoadStateHeaderAndFooter(
            header = PostsLoadStateCoinSearchAdapter(coinSearchAdapter),
            footer = PostsLoadStateCoinSearchAdapter(coinSearchAdapter)
        )
    }

    private fun updatedCoinFromInput() {
        binding.input.text.trim().toString().let {
            if (it.isNotBlank()) {
                binding.recyclerview.visibility = View.GONE
                viewModel.searchCoinByCoinName(it)
                binding.recyclerview.visibility = View.GONE
                binding.searchRecyclerview.visibility = View.VISIBLE
                val count = binding.searchRecyclerview.adapter?.itemCount
                binding.searchRecyclerview.adapter?.notifyDataSetChanged()
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.getSearchResult()?.collectLatest {
                        coinSearchAdapter.submitData(it)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}