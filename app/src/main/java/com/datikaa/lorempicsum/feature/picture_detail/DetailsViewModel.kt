package com.datikaa.lorempicsum.feature.picture_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.datikaa.lorempicsum.feature.picture_detail.dynamics.DetailsIntent
import com.datikaa.lorempicsum.feature.picture_detail.dynamics.DetailsState
import com.datikaa.lorempicsum.feature.picture_detail.tools.BlurValue
import com.datikaa.lorempicsum.feature.picture_detail.tools.DetailsFragmentPicsumArg
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
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
            blurValue = BlurValue(1),
            layoutState = DetailsState.LayoutState.Init,
        )
        _state = MutableStateFlow(initState)
        state = _state.stateIn(viewModelScope, SharingStarted.Eagerly, initState)
        viewModelScope.launch {
            delay(50) // just to have a little time for the initState to work properly
            val originalPicState = _state.value.copy(
                pictureUrl = picsumArg.downloadUrl,
                layoutState = DetailsState.LayoutState.NoBlur,
            )
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
            pictureUrl = "${picsumArg.downloadUrl}?blur=${blurValue.value}",
            layoutState = DetailsState.LayoutState.ShowBlur,
        ).postState()
    }

    private fun changeStateBlurValue(blurValue: BlurValue) = with(_state.value) {
        if (this.blurValue == blurValue) return@with
        copy(
            blurValue = blurValue,
            selectedButton = DetailsState.SelectedButton.Blur,
            pictureUrl = "${picsumArg.downloadUrl}?blur=${blurValue.value}",
            layoutState = DetailsState.LayoutState.ShowBlur,
        ).postState()
    }

    private fun changeStateToGrayScale() = with(_state.value) {
        copy(
            selectedButton = DetailsState.SelectedButton.GreyScale,
            pictureUrl = "${picsumArg.downloadUrl}?grayscale",
            layoutState = DetailsState.LayoutState.NoBlur,
        ).postState()
    }

    private fun changeStateToOriginal() = with(_state.value) {
        copy(
            selectedButton = DetailsState.SelectedButton.Original,
            pictureUrl = picsumArg.downloadUrl,
            layoutState = DetailsState.LayoutState.NoBlur,
        ).postState()
    }

    private fun DetailsState.postState() {
        _state.value = this
    }
}