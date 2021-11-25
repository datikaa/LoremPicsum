package com.datikaa.lorempicsum.feature.picture_detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetailsViewModel(
    url: String,
) : ViewModel() {

    private val _state: MutableStateFlow<DetailsState>
    val state: StateFlow<DetailsState>

    init {
        val initState = DetailsState(
            pictureUrl = url,
            selectedButton = DetailsState.SelectedButton.Original,
            blurValue = null
        )
        _state = MutableStateFlow(initState)
        state = _state
    }

    fun submitIntent(detailsIntent: DetailsIntent) {
        when (detailsIntent) {
            DetailsIntent.Blur -> changeStateToBlur()
            DetailsIntent.GrayScale -> changeStateToGrayScale()
            DetailsIntent.Original -> changeStateToOriginal()
            is DetailsIntent.BlurValueChange -> TODO()
        }
    }

    private fun changeStateToBlur() {
        _state.value = _state.value.copy(
            selectedButton = DetailsState.SelectedButton.Blur
        )
    }

    private fun changeStateToGrayScale() {
        _state.value = _state.value.copy(
            selectedButton = DetailsState.SelectedButton.GreyScale
        )
    }

    private fun changeStateToOriginal() {
        _state.value = _state.value.copy(
            selectedButton = DetailsState.SelectedButton.Original
        )
    }
}