package com.datikaa.lorempicsum.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.datikaa.lorempicsum.data.ListItem
import com.datikaa.lorempicsum.network.RetrofitFactory
import com.datikaa.lorempicsum.ui.main.model.MainItemModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _models = MutableStateFlow<List<MainItemModel>>(emptyList())
    val models: StateFlow<List<MainItemModel>> = _models

    init {
        viewModelScope.launch {
            val currentListOfModels = _models.value.toMutableList()
            currentListOfModels += RetrofitFactory.build().list().map { it.toUiModel() }
            _models.emit(currentListOfModels)
        }
    }
}

private fun ListItem.toUiModel() = MainItemModel(
    id = id,
    url = "https://picsum.photos/id/${width / 2 as Int}/${height / 2 as Int}",
)