package com.datikaa.lorempicsum.feature.main_pager

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.datikaa.lorempicsum.ScreenMetrics
import com.datikaa.lorempicsum.domain.data.PicsumPicture
import com.datikaa.lorempicsum.network.response.PicsumResponseItem
import com.datikaa.lorempicsum.feature.main_pager.model.MainPagerItemModel
import com.datikaa.lorempicsum.feature.main_pager.paging.MainPagerPagingSource
import com.datikaa.lorempicsum.network.PicsumService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainPagerViewModel(
    private val screenMetrics: ScreenMetrics,
    private val picsumService: PicsumService,
) : ViewModel() {

    init {
        Log.d("teszt", "screen size: ${screenMetrics.width}/${screenMetrics.height}")
    }

    val flow = Pager(
        PagingConfig(
            pageSize = 20,
            prefetchDistance = 20,
        )
    ) {
        MainPagerPagingSource(picsumService)
    }.flow.map { pagingData ->
        pagingData.map { picsumPicture ->
            picsumPicture.calculateSize().also {
                Log.d("teszt", "final size: ${it.width}/${it.height}")
            }
        }
    }.cachedIn(viewModelScope)

    /**
     * Recursively calculate image size by reducing it in 10% increments
     */
    private fun PicsumPicture.calculateSize(): PicsumPicture {
        return if (width > screenMetrics.width / 2) {
            copy(
                width = (width / 1.1).toInt(),
                height = (height / 1.1).toInt(),
            ).calculateSize()
        } else this
    }
}