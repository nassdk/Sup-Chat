package com.nassdk.supchat

import android.app.Application
import com.example.di.module.dataModule
import com.example.di.module.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppDelegate : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {

        startKoin {
            androidContext(this@AppDelegate)
            modules(
                    domainModule,
                    dataModule
            )
        }
    }
}