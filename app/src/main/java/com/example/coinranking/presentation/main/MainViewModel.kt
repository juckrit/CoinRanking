package com.example.coinranking.presentation.main

import androidx.lifecycle.ViewModel
import com.example.coinranking.domain.usecase.GetCoinUseCase

class MainViewModel(getCoinUseCase: GetCoinUseCase) : ViewModel() {

    private val coins = getCoinUseCase.execute()

    //            .cachedIn(viewModelScope)
    fun getCoins() = coins

}