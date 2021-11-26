package com.datikaa.lorempicsum.feature.main_pager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.datikaa.lorempicsum.domain.data.PicsumPicture
import com.datikaa.lorempicsum.feature.main_pager.dynamics.MainPagerIntent
import com.datikaa.lorempicsum.feature.main_pager.dynamics.MainPagerDestination
import com.datikaa.lorempicsum.feature.main_pager.paging.MainPagerPagingSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class MainPagerViewModel(
    private val mainPagerPagingSource: MainPagerPagingSource,
) : ViewModel() {

    val pagerDataFlow = Pager(
        PagingConfig(
            pageSize = 20,
            prefetchDistance = 20,
        )
    ) {
        mainPagerPagingSource
    }.flow.cachedIn(viewModelScope)

    private val _navigationFlow = MutableSharedFlow<MainPagerDestination>(replay = 0)
    val navigationFlow: SharedFlow<MainPagerDestination> = _navigationFlow

    fun submitIntent(intent: MainPagerIntent) = when(intent) {
        is MainPagerIntent.ShowDetails -> navigateToDetails(intent.picsumPicture)
    }

    private fun navigateToDetails(picsumPicture: PicsumPicture) {
        MainPagerDestination.Details(picsumPicture).dispatchNavigation()
    }

    private fun MainPagerDestination.dispatchNavigation() {
        viewModelScope.launch {
            _navigationFlow.emit(this@dispatchNavigation)
        }
    }
}