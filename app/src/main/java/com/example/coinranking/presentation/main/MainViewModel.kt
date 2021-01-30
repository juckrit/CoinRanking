package com.example.coinranking.presentation.main

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.coinranking.data.CoinCoinsModel
import com.example.coinranking.domain.usecase.GetCoinUseCase
import kotlinx.coroutines.flow.Flow

class MainViewModel(private val getCoinUseCase: GetCoinUseCase) : ViewModel() {

    private var coins: Flow<PagingData<CoinCoinsModel>>? = null

    //            .cachedIn(viewModelScope)
    fun getCoins() = coins

    fun fetchCoin(){
        val result = getCoinUseCase.execute()
        coins = result
    }

    fun refresh(){
        coins = null
        coins = getCoinUseCase.execute()
    }
}