package com.example.coinranking.data.repository

import android.util.Log
import androidx.paging.PagingSource
import com.example.coinranking.data.CoinCoinsModel
import com.example.coinranking.data.api.CoinRankingService
import com.example.coinranking.domain.repository.CoinRepository

class CoinRepositoryImpl(private val coinRemoteDataSource: CoinRemoteDataSource) : CoinRepository,
    PagingSource<Int, CoinCoinsModel>() {

    override fun getCoinsServices(): CoinRankingService {
        return coinRemoteDataSource.getCoinRankingService()
    }

    override suspend fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, CoinCoinsModel> {
        try {
            val currentPage = params.key ?: 0
            val response = getCoinsServices().getCoins(offset = currentPage * 10)
            val status = response.status
            val coinList = response.data.coins
            Log.d("test size",coinList.size.toString())
            Log.d("test page",currentPage.toString())
            Log.d("test offset",(currentPage * 10).toString())
            val loadResult = PagingSource.LoadResult.Page(
                data = response.data.coins,
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (coinList.isNotEmpty()) {
                    currentPage + 1
                } else {
                    null
                }
            )
            return loadResult
        } catch (e: Exception) {
            Log.d("test","fail")
            return PagingSource.LoadResult.Error(e)
        }
    }
}

