package com.example.coinranking.presentation.di

import com.example.coinranking.data.api.CoinRankingService
import com.example.coinranking.data.repository.CoinRepositoryImpl
import com.example.coinranking.data.repository.datasource.CoinRemoteDataSource
import com.example.coinranking.data.repository.datasourceImpl.CoinRemoteDataSourceImpl
import com.example.coinranking.domain.repository.CoinRepository
import com.example.coinranking.domain.usecase.GetCoinUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single<CoinRankingService>(named(DI_NAME_CoinRankingService)) {
        CoinRankingService.instance
    }


    single<CoinRemoteDataSource>(named(DI_NAME_CoinRemoteDataSource)) {
        CoinRemoteDataSourceImpl(get(named(DI_NAME_CoinRankingService)))
    }

    single<CoinRepository>(named(DI_NAME_CoinRepository)) {
        CoinRepositoryImpl(get(named(DI_NAME_CoinRemoteDataSource)))
    }

    single<GetCoinUseCase>(named(DI_NAME_DI_NAME_GetCoinUseCase)) {
        GetCoinUseCase(get(named(DI_NAME_CoinRepository)))
    }





}