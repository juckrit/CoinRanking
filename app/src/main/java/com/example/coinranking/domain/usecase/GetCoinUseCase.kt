package com.example.coinranking.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.coinranking.data.CoinCoinsModel
import com.example.coinranking.data.repository.CoinRemoteDataSource
import com.example.coinranking.data.repository.CoinRemoteDataSourceImpl
import com.example.coinranking.data.repository.CoinRemotePagingSource
import com.example.coinranking.data.repository.CoinRepositoryImpl
import com.example.coinranking.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow

class GetCoinUseCase(
    private val coinRemoteDataSource: CoinRemoteDataSource
) {
    fun execute(): Flow<PagingData<CoinCoinsModel>> {
        val result = Pager(PagingConfig(pageSize = 10)) {
//            CoinRemotePagingSource(coinRepository.getCoinsServices())
            CoinRepositoryImpl(coinRemoteDataSource)
        }.flow
        return result
    }
}