package com.example.coinranking.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.coinranking.data.CoinCoinsModel
import com.example.coinranking.data.repository.CoinRepositoryImpl
import com.example.coinranking.domain.usecase.GetCoinUseCase
import com.example.coinranking.presentation.helper.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(private val getCoinUseCase: GetCoinUseCase) : ViewModel() {

    private var coins: Flow<PagingData<CoinCoinsModel>>? = null

    //            .cachedIn(viewModelScope)
    fun getCoins() = coins

    fun fetchCoin(){
        viewModelScope.launch(Dispatchers.IO){
            val result = getCoinUseCase.execute()
            coins = result
        }

    }

    fun getNetworkState(): LiveData<NetworkState>? {
        return CoinRepositoryImpl.getNetworkState()
    }

    fun setNetworkStateToNull() {
        CoinRepositoryImpl.setNetworkStateToNull()
    }

    fun initNetworkState(){
        CoinRepositoryImpl.initNetworkState()
    }
}