package com.freisia.vueee

import android.app.Application
import com.freisia.vueee.di.networkModule
import com.freisia.vueee.di.repositoryModule
import com.freisia.vueee.di.viewmodelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@App)
            modules(networkModule)
            modules(repositoryModule)
            modules(viewmodelModule)
        }
    }
}