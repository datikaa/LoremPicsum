package com.datikaa.lorempicsum.feature.main_pager


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.datikaa.lorempicsum.feature.main_pager.paging.MainPagerPagingSource

class MainPagerViewModel(
    private val mainPagerPagingSource: MainPagerPagingSource,
) : ViewModel() {

    val flow = Pager(
        PagingConfig(
            pageSize = 20,
            prefetchDistance = 20,
        )
    ) {
        mainPagerPagingSource
    }.flow.cachedIn(viewModelScope)
}