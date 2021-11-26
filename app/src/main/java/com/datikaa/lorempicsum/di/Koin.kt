package com.datikaa.lorempicsum.di

import com.datikaa.lorempicsum.feature.main_pager.MainPagerViewModel
import com.datikaa.lorempicsum.feature.main_pager.paging.MainPagerPagingSource
import com.datikaa.lorempicsum.feature.picture_detail.DetailsViewModel
import com.datikaa.lorempicsum.network.RetrofitFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainPagerViewModel(mainPagerPagingSource = get()) }
    viewModel { DetailsViewModel(picsumArg = get(), picsumService = get()) }
}

val repositoryModule = module {
    factory { MainPagerPagingSource(picsumService = get()) }
}

val networkModule = module {
    single { RetrofitFactory.build() }
}