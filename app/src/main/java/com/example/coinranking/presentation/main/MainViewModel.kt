package com.example.coinranking.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.coinranking.data.CoinCoinsModel
import com.example.coinranking.data.api.CoinRankingService
import com.example.coinranking.data.repository.CoinPagingSource
import com.example.coinranking.domain.usecase.GetCoinUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getCoinUseCase: GetCoinUseCase,
    private val coinRankingService: CoinRankingService
) : ViewModel() {

    private var coins: Flow<PagingData<CoinCoinsModel>>? = null

    //            .cachedIn(viewModelScope)
    fun getCoins() = coins

    fun fetchCoin() {
//        viewModelScope.launch(Dispatchers.IO){
//            val result = getCoinUseCase.execute()
//            coins = result
//        }
        coins = Pager(PagingConfig(pageSize = 10)) {
            CoinPagingSource(coinRankingService)
        }.flow

    }

    suspend fun refresh() {
        coins = null
//        coins = getCoinUseCase.execute()

        coins = Pager(PagingConfig(pageSize = 10)) {
            CoinPagingSource(coinRankingService)
        }.flow

    }
}