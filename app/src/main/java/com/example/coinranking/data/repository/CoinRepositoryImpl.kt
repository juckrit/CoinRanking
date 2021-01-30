package com.example.coinranking.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import com.example.coinranking.LOAD_LIMIT
import com.example.coinranking.data.CoinCoinsModel
import com.example.coinranking.data.api.CoinRankingService
import com.example.coinranking.domain.repository.CoinRepository
import com.example.coinranking.presentation.helper.NetworkState

class CoinRepositoryImpl(private val coinRemoteDataSource: CoinRemoteDataSource) : CoinRepository,
    PagingSource<Int, CoinCoinsModel>() {


    companion object {
        private var networkState: MutableLiveData<NetworkState>? = MutableLiveData<NetworkState>();

        fun getNetworkState(): LiveData<NetworkState>? {
            return networkState
        }

        fun setNetworkStateToNull() {
            networkState = null
        }

        fun initNetworkState(){
            networkState = MutableLiveData<NetworkState>()
        }
    }

    override fun getCoinsServices(): CoinRankingService {
        return coinRemoteDataSource.getCoinRankingService()
    }

    override suspend fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, CoinCoinsModel> {
        networkState?.postValue(NetworkState.getLoadingInstance())

        try {
            val currentPage = params.key ?: 0
            val response =
                getCoinsServices().getCoins(LOAD_LIMIT, offset = currentPage * LOAD_LIMIT)
            val status = response.status
            val coinList = response.data.coins
            Log.d("test size", coinList.size.toString())
            Log.d("test page", currentPage.toString())
            Log.d("test offset", (currentPage * LOAD_LIMIT).toString())
            val loadResult = PagingSource.LoadResult.Page(
                data = response.data.coins,
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (coinList.isNotEmpty()) {
                    currentPage + 1
                } else {
                    null
                }
            )
            networkState?.postValue(NetworkState.getLoadedInstance())
            return loadResult
        } catch (e: Exception) {
            Log.d("test", "fail")
            networkState?.postValue(NetworkState(NetworkState.Status.FAILED, e.message ?: "fail"))
            return PagingSource.LoadResult.Error(e)
        }
    }
}

