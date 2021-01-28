package com.example.coinranking.domain.usecase

import com.example.coinranking.data.GetCoinResponseModel
import com.example.coinranking.domain.repository.CoinRepository

class GetCoinUseCase(
    private val coinRepository: CoinRepository
) {
    suspend fun execute(albumId:Int): GetCoinResponseModel {
        return coinRepository.getCoins()
//        return emptyList()
    }
}