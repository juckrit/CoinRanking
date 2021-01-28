package com.example.coinranking.data.repository.datasourceImpl

import com.example.coinranking.data.GetCoinResponseModel
import com.example.coinranking.data.api.CoinRankingService
import com.example.coinranking.data.repository.datasource.CoinRemoteDataSource

class CoinRemoteDataSourceImpl(
    private val coinRankingService: CoinRankingService
): CoinRemoteDataSource {
    override suspend fun getCoins(): GetCoinResponseModel {
        val response = coinRankingService.getCoins()
        return response
    }
}