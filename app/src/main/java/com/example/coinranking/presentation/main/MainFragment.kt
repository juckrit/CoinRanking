package com.example.coinranking.presentation.main

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.coinranking.databinding.FragmentMainBinding
import com.example.coinranking.presentation.di.DI_NAME_MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class MainFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by inject((named(DI_NAME_MainViewModel)))
    private lateinit var coinAdapter: CoinPagingDataAdapter


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
        addlistener()
        observer()
    }


    private fun addlistener() {
        binding.swiperefresh.setOnRefreshListener(this)

    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getCoins()?.collectLatest {
                coinAdapter.submitData(it)
            }
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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onRefresh() {
        viewModel.refresh()
    }
}