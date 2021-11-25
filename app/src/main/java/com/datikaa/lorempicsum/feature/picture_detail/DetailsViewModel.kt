package com.datikaa.lorempicsum.feature.picture_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
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
            blurValue = BlurValue(1)
        )
        _state = MutableStateFlow(initState)
        state = _state.stateIn(viewModelScope, SharingStarted.Eagerly, initState)
        viewModelScope.launch {
            delay(50) // just to have a little time for the initState to work properly
            val originalPicState = _state.value.copy(pictureUrl = picsumArg.downloadUrl)
            _state.emit(originalPicState)
        }
    }

    fun submitIntent(detailsIntent: DetailsIntent) {
        when (detailsIntent) {
            DetailsIntent.Blur -> changeStateToBlur()
            DetailsIntent.GrayScale -> changeStateToGrayScale()
            DetailsIntent.Original -> changeStateToOriginal()
            is DetailsIntent.BlurValueChange -> changeStateBlurValue(detailsIntent.blurValue)
        }
    }

    private fun changeStateToBlur() = with(_state.value) {
        copy(
            selectedButton = DetailsState.SelectedButton.Blur,
            pictureUrl = "${picsumArg.downloadUrl}?blur=${blurValue.value}"
        ).postState()
    }

    private fun changeStateBlurValue(blurValue: BlurValue) = with(_state.value) {
        if (this.blurValue == blurValue) return@with
        copy(
            blurValue = blurValue,
            selectedButton = DetailsState.SelectedButton.Blur,
            pictureUrl = "${picsumArg.downloadUrl}?blur=${blurValue.value}"
        ).postState()
    }

    private fun changeStateToGrayScale() = with(_state.value) {
        copy(
            selectedButton = DetailsState.SelectedButton.GreyScale,
            pictureUrl = "${picsumArg.downloadUrl}?grayscale"
        ).postState()
    }

    private fun changeStateToOriginal() = with(_state.value) {
        copy(
            selectedButton = DetailsState.SelectedButton.Original,
            pictureUrl = picsumArg.downloadUrl
        ).postState()
    }

    private fun DetailsState.postState() {
        _state.value = this
    }
}