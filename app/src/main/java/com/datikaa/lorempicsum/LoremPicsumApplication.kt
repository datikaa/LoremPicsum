package com.datikaa.lorempicsum

import android.app.Application
import org.koin.core.context.startKoin

class LoremPicsumApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {

        }
    }
}