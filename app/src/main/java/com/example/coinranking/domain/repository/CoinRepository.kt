package com.example.coinranking.domain.repository

import com.example.coinranking.data.GetCoinResponseModel

interface CoinRepository {
    suspend fun getCoins(): GetCoinResponseModel
}