package com.example.coinranking.data.repository.datasource

import com.example.coinranking.data.GetCoinResponseModel

interface CoinRemoteDataSource {
    suspend fun getCoins(): GetCoinResponseModel
}