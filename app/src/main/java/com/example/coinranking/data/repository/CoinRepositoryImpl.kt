package com.example.coinranking.data.repository

import com.example.coinranking.data.GetCoinResponseModel
import com.example.coinranking.data.repository.datasource.CoinRemoteDataSource
import com.example.coinranking.domain.repository.CoinRepository

class CoinRepositoryImpl(
    private val coinRemoteDataSource: CoinRemoteDataSource
): CoinRepository {
    override suspend fun getCoins(): GetCoinResponseModel {
        val response = coinRemoteDataSource.getCoins()
        return  response
    }

}