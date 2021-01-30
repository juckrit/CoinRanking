package com.example.coinranking.data.repository

import com.example.coinranking.data.api.CoinRankingService

class CoinRemoteDataSourceImpl(
    private val coinRankingService: CoinRankingService
) : CoinRemoteDataSource {
    override fun getCoinRankingService(): CoinRankingService {
        return coinRankingService
    }
}