package com.datikaa.lorempicsum

import android.app.Application
import com.datikaa.lorempicsum.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LoremPicsumApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@LoremPicsumApplication)
            // declare modules
            modules(viewModelsModule)
        }
    }
}