package com.example.coinranking.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.coinranking.data.CoinCoinsModel
import com.example.coinranking.data.repository.CoinRemoteDataSource
import com.example.coinranking.domain.usecase.GetCoinUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getCoinUseCase: GetCoinUseCase,
    private val coinRemoteDataSource: CoinRemoteDataSource
) : ViewModel() {

    private var coins: Flow<PagingData<CoinCoinsModel>>? = null

    fun getCoins() = coins

    fun fetchCoin() {
        val result = getCoinUseCase.execute()
        coins = result
//        searchResult = getCoinUseCase.execute()

    }


    private var searchResult: Flow<PagingData<CoinCoinsModel>>? = null

    //            .cachedIn(viewModelScope)
    fun getSearchResult(): Flow<PagingData<CoinCoinsModel>>? {
        return searchResult
    }

    fun setSearchResult(flow: Flow<PagingData<CoinCoinsModel>>) {
        searchResult = flow
    }


    fun searchCoinByCoinName(coinName: String) {
        viewModelScope.launch {
            searchResult = getCoinUseCase.searchByName(coinName)
        }
    }
}