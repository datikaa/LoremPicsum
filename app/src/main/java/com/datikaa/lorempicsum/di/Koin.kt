package com.datikaa.lorempicsum.di

import com.datikaa.lorempicsum.feature.main_pager.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { MainViewModel() }
}