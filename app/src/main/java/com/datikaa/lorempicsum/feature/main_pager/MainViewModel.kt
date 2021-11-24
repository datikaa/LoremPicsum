package com.datikaa.lorempicsum.feature.main_pager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.datikaa.lorempicsum.network.response.ListItem
import com.datikaa.lorempicsum.network.RetrofitFactory
import com.datikaa.lorempicsum.feature.main_pager.model.MainItemModel
import com.datikaa.lorempicsum.network.PicsumService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val picsumService: PicsumService,
) : ViewModel() {

    private val _models = MutableStateFlow<List<MainItemModel>>(emptyList())
    val models: StateFlow<List<MainItemModel>> = _models

    init {
        viewModelScope.launch {
            val currentListOfModels = _models.value.toMutableList()
            currentListOfModels += picsumService.list().map { it.toUiModel() }
            _models.emit(currentListOfModels)
        }
    }

    private fun ListItem.toUiModel() = MainItemModel(
        id = id,
        url = downloadUrl,
    )
}