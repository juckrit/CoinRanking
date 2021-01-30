package com.example.coinranking.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.coinranking.LOAD_LIMIT
import com.example.coinranking.data.CoinCoinsModel
import com.example.coinranking.data.repository.CoinRemoteDataSource
import com.example.coinranking.data.repository.CoinRepositoryImpl
import com.example.coinranking.presentation.helper.NetworkState
import kotlinx.coroutines.flow.Flow

class GetCoinUseCase(
    private val coinRemoteDataSource: CoinRemoteDataSource
) {


    suspend fun execute(): Flow<PagingData<CoinCoinsModel>> {
        val result = Pager(PagingConfig(pageSize = LOAD_LIMIT,
            enablePlaceholders = false,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED
        )) {
//            CoinRemotePagingSource(coinRepository.getCoinsServices())
            CoinRepositoryImpl(coinRemoteDataSource)
        }.flow
        return result
    }

//    fun getNetworkState(): LiveData<NetworkState> {
//        return coinRepositoryImpl.getNetworkState()
//    }

}