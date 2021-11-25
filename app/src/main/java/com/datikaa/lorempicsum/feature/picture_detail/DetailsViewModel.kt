package com.datikaa.lorempicsum.feature.picture_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val picsumArg: DetailsFragmentPicsumArg,
) : ViewModel() {

    private val _state: MutableStateFlow<DetailsState>
    val state: StateFlow<DetailsState>

    init {
        val initState = DetailsState(
            pictureUrl = picsumArg.url,
            selectedButton = DetailsState.SelectedButton.Original,
            blurValue = null
        )
        _state = MutableStateFlow(initState)
        state = _state
        viewModelScope.launch {
            delay(50)
            val originalPicState = _state.value.copy(pictureUrl = picsumArg.downloadUrl)
            _state.emit(originalPicState)
        }
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
            selectedButton = DetailsState.SelectedButton.Blur,
            pictureUrl = "${picsumArg.downloadUrl}?blur"
        )
    }

    private fun changeStateToGrayScale() {
        _state.value = _state.value.copy(
            selectedButton = DetailsState.SelectedButton.GreyScale,
            pictureUrl = "${picsumArg.downloadUrl}?grayscale"
        )
    }

    private fun changeStateToOriginal() {
        _state.value = _state.value.copy(
            selectedButton = DetailsState.SelectedButton.Original,
            pictureUrl = picsumArg.downloadUrl
        )
    }
}