package com.example.coinranking.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.coinranking.LOAD_LIMIT
import com.example.coinranking.data.CoinCoinsModel
import com.example.coinranking.data.repository.CoinRemoteDataSource
import com.example.coinranking.data.repository.CoinRepositoryImpl
import kotlinx.coroutines.flow.Flow

class GetCoinUseCase(
    private val coinRemoteDataSource: CoinRemoteDataSource
) {
    fun execute(): Flow<PagingData<CoinCoinsModel>> {
        val result = Pager(PagingConfig(pageSize = LOAD_LIMIT,
            enablePlaceholders = false,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED
        )) {
//            CoinRemotePagingSource(coinRepository.getCoinsServices())
            CoinRepositoryImpl(coinRemoteDataSource)
        }.flow
        return result
    }
}