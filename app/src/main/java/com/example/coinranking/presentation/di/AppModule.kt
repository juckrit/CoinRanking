package com.example.coinranking.presentation.di

import com.example.coinranking.data.api.CoinRankingService
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single<CoinRankingService>(named(DI_NAME_CoinRankingService)) {
        CoinRankingService.instance
    }
}