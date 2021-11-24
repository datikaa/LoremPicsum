package com.datikaa.lorempicsum

import android.app.Application
import com.datikaa.lorempicsum.di.networkModule
import com.datikaa.lorempicsum.di.repositoryModule
import com.datikaa.lorempicsum.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LoremPicsumApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@LoremPicsumApplication)
            modules(
                viewModelModule,
                repositoryModule,
                networkModule,
            )
        }
    }
}