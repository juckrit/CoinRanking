package com.example.coinranking.domain.repository

import com.example.coinranking.data.api.CoinRankingService

interface CoinRepository {
    fun getCoinsServices(): CoinRankingService
}