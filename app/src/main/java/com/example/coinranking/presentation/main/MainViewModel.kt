package com.example.coinranking.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import com.example.coinranking.data.CoinCoinsModel
import com.example.coinranking.domain.usecase.GetCoinUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel(private val getCoinUseCase: GetCoinUseCase) : ViewModel() {

    private var coins: Flow<PagingData<CoinCoinsModel>>? = null
    private var searchResult: Flow<PagingData<CoinCoinsModel>>? = null

    //            .cachedIn(viewModelScope)
    fun getCoins() = coins
    fun getSearchResult() = searchResult

    fun fetchCoin() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getCoinUseCase.execute()
            coins = result
        }


    }

    fun searchCoinByCoinName(coinName: String) {
        viewModelScope.launch(Dispatchers.IO) {
//            val result = getCoinUseCase.execute()
//            searchResult = result.map { pagingData ->
//                pagingData.filter { coinCoinsModel ->
//                    coinCoinsModel.name.equals(coinName)
//                }
//            }
            searchResult = coins?.map { it ->
                val pagingData = it
                pagingData.filter { coinCoinsModel ->
                    coinCoinsModel.id==1
                }
            }
        }

        val a = 1


    }

    suspend fun refresh() {
        coins = null
        coins = getCoinUseCase.execute()

    }
}