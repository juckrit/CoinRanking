package com.example.coinranking.presentation.main

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import com.example.coinranking.data.CoinCoinsModel
import com.example.coinranking.data.repository.CoinRemoteDataSource
import com.example.coinranking.data.repository.CoinRepositoryImpl
import com.example.coinranking.domain.usecase.GetCoinUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MainViewModel(
    private val getCoinUseCase: GetCoinUseCase,
    private val coinRemoteDataSource: CoinRemoteDataSource
) : ViewModel() {

    private var coins: Flow<PagingData<CoinCoinsModel>>? = null
    private var searchResult: Flow<PagingData<CoinCoinsModel>>? = null

    //            .cachedIn(viewModelScope)
    fun getCoins() = coins
    fun getSearchResult(): Flow<PagingData<CoinCoinsModel>>? {
        return searchResult
    }

    fun setSearchResult(flow: Flow<PagingData<CoinCoinsModel>>) {
        searchResult = flow
    }


    fun fetchCoin() {
        val result = getCoinUseCase.execute()
        coins = result
    }

    fun searchCoinByCoinName(coinName: String) {
        try {
            searchResult = null
            searchResult = coins
            searchResult?.map { it ->
                val pagingData = it
                pagingData.filterSync { coinCoinsModel ->
                    coinCoinsModel.name.equals(coinName)
                }
            }


        } catch (e: Exception) {
            val a = e
        }
    }
}