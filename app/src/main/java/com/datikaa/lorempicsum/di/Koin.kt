package com.datikaa.lorempicsum.di

import com.datikaa.lorempicsum.feature.main_pager.MainPagerViewModel
import com.datikaa.lorempicsum.network.RetrofitFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainPagerViewModel(
            picsumService = get(),
        )
    }
}

val networkModule = module {
    single { RetrofitFactory.build() }
}