package com.example.coinranking.data.api

import com.example.coinranking.BuildConfig
import com.example.coinranking.data.GetCoinResponseModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface CoinRankingService {


    //https://api.coinranking.com/v1/public/coins?limit=2&offset=0
    @GET("coins")
    suspend fun getCoins(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): GetCoinResponseModel




    companion object {
        val client = OkHttpClient().newBuilder()
            .build()
        val instance: CoinRankingService by lazy {
            Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
                .create(CoinRankingService::class.java)
        }
    }
}