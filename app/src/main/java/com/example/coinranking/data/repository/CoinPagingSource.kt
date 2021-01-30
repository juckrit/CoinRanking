package com.example.coinranking.data.repository

import androidx.paging.PagingSource
import com.example.coinranking.data.CoinCoinsModel
import com.example.coinranking.data.api.CoinRankingService

class CoinPagingSource(
    private val coinRankingService: CoinRankingService
) : PagingSource<Int, CoinCoinsModel>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CoinCoinsModel> {
        try {
            val currentPage = params.key ?: 0
            val response = coinRankingService.getCoins(offset = currentPage * 10)
            val status = response.status
            val coinList = response.data.coins
            val loadResult = LoadResult.Page(
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
            return LoadResult.Error(e)
        }
    }
}
