package com.example.coinranking.data.repository

import com.example.coinranking.data.api.CoinRankingService

interface CoinRemoteDataSource {
    fun getCoinRankingService(): CoinRankingService
}