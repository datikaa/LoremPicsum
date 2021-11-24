package com.datikaa.lorempicsum.feature.main_pager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.datikaa.lorempicsum.network.response.PicsumResponseItem
import com.datikaa.lorempicsum.feature.main_pager.model.MainPagerItemModel
import com.datikaa.lorempicsum.feature.main_pager.paging.MainPagerPagingSource
import com.datikaa.lorempicsum.network.PicsumService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainPagerViewModel(
    private val picsumService: PicsumService,
) : ViewModel() {

    private val _models = MutableStateFlow<List<MainPagerItemModel>>(emptyList())
    val models: StateFlow<List<MainPagerItemModel>> = _models

    val flow = Pager(
        PagingConfig(
            pageSize = 20,
            prefetchDistance = 20,
        )
    ) {
        MainPagerPagingSource(picsumService)
    }.flow.cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            val currentListOfModels = _models.value.toMutableList()
            currentListOfModels += picsumService.list().map { it.toUiModel() }
            _models.emit(currentListOfModels)
        }
    }

    private fun PicsumResponseItem.toUiModel() = MainPagerItemModel(
        id = id,
        url = downloadUrl,
    )
}