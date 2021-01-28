package com.example.coinranking.di

import android.app.Application
import android.content.Context
import com.example.coinranking.presentation.di.appModule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.check.checkModules
import org.mockito.Mockito

class AppModuleTest {
    private val context: Context = Mockito.mock(Application::class.java)


    @Test
    fun `Test Koin Modules`() {
        startKoin {
            androidContext(context)
            modules(listOf(appModule))
        }.checkModules()

        stopKoin()
    }
}