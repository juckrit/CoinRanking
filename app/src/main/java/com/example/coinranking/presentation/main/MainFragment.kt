package com.example.coinranking.presentation.main

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.coinranking.databinding.FragmentMainBinding
import com.example.coinranking.presentation.di.DI_NAME_MainViewModel
import com.example.coinranking.presentation.helper.NetworkState
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
        viewModel.getNetworkState()?.observe(viewLifecycleOwner, Observer{
            when(it.mStatus){
                NetworkState.Status.RUNNING ->{
                    Toast.makeText(requireContext(),"loading",Toast.LENGTH_SHORT).show()
                }
                NetworkState.Status.FAILED->{
                    Toast.makeText(requireContext(),it.mMsg,Toast.LENGTH_SHORT).show()
                }
                NetworkState.Status.SUCCESS->{
                    Toast.makeText(requireContext(),"load success",Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun setup() {
        viewModel.initNetworkState()
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
        viewModel.setNetworkStateToNull()
    }

    override fun onRefresh() {
        if (!binding.swiperefresh.isRefreshing()) {
            binding.swiperefresh.post(Runnable { binding.swiperefresh.setRefreshing(true) })
        }
        viewLifecycleOwner.lifecycleScope.launch {
            coinAdapter.refresh()
            if (binding.swiperefresh.isRefreshing()) {
                binding.swiperefresh.post(Runnable { binding.swiperefresh.setRefreshing(false) })
            }
        }
    }
}