package com.datikaa.lorempicsum.feature.picture_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.datikaa.lorempicsum.feature.picture_detail.dynamics.DetailsDestination
import com.datikaa.lorempicsum.feature.picture_detail.dynamics.DetailsIntent
import com.datikaa.lorempicsum.feature.picture_detail.dynamics.DetailsState
import com.datikaa.lorempicsum.feature.picture_detail.tools.BlurValue
import com.datikaa.lorempicsum.feature.picture_detail.tools.DetailsFragmentPicsumArg
import com.datikaa.lorempicsum.network.PicsumService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val picsumService: PicsumService,
    private val picsumArg: DetailsFragmentPicsumArg,
) : ViewModel() {

    private val _navigationFlow = MutableSharedFlow<DetailsDestination>(replay = 0)
    val navigationFlow: SharedFlow<DetailsDestination> = _navigationFlow

    private val _state: MutableStateFlow<DetailsState>
    val state: StateFlow<DetailsState>

    init {
        val initState = initialState()
        _state = MutableStateFlow(initState)
        state = _state.stateIn(viewModelScope, SharingStarted.Eagerly, initState)
        viewModelScope.launch {
            delay(50) // just to have a little time for the initState to work properly
            state.value.copy(
                pictureUrl = picsumArg.downloadUrl,
                layoutState = DetailsState.LayoutState.NoBlur,
            ).postState()
            val picsumResponseItem = picsumService.info(picsumArg.id)
            state.value.copy(
                info = DetailsState.Info(
                    id = "${picsumResponseItem.id}",
                    author = picsumResponseItem.author,
                    width = "${picsumResponseItem.width}",
                    height = "${picsumResponseItem.height}",
                    url = picsumResponseItem.url,
                    downloadUrl = picsumResponseItem.downloadUrl,
                )
            ).postState()
        }
    }

    fun submitIntent(detailsIntent: DetailsIntent) {
        when (detailsIntent) {
            DetailsIntent.Blur -> changeStateToBlur()
            DetailsIntent.GrayScale -> changeStateToGrayScale()
            DetailsIntent.Original -> changeStateToOriginal()
            DetailsIntent.Info -> changeStateToInfo()
            DetailsIntent.CloseInfo -> changeStateToCloseInfo()
            DetailsIntent.Share -> initiateShare()
            is DetailsIntent.BlurValueChange -> changeStateBlurValue(detailsIntent.blurValue)
        }
    }

    private fun changeStateToBlur() = with(state.value) {
        copy(
            selectedButton = DetailsState.SelectedButton.Blur,
            pictureUrl = "${picsumArg.downloadUrl}?blur=${blurValue.value}",
            layoutState = DetailsState.LayoutState.ShowBlur,
        ).postState()
    }

    private fun changeStateBlurValue(blurValue: BlurValue) = with(state.value) {
        if (this.blurValue == blurValue) return@with
        copy(
            blurValue = blurValue,
            selectedButton = DetailsState.SelectedButton.Blur,
            pictureUrl = "${picsumArg.downloadUrl}?blur=${blurValue.value}",
            layoutState = DetailsState.LayoutState.ShowBlur,
        ).postState()
    }

    private fun changeStateToGrayScale() = with(state.value) {
        copy(
            selectedButton = DetailsState.SelectedButton.GreyScale,
            pictureUrl = "${picsumArg.downloadUrl}?grayscale",
            layoutState = DetailsState.LayoutState.NoBlur,
        ).postState()
    }

    private fun changeStateToOriginal() = with(state.value) {
        copy(
            selectedButton = DetailsState.SelectedButton.Original,
            pictureUrl = picsumArg.downloadUrl,
            layoutState = DetailsState.LayoutState.NoBlur,
        ).postState()
    }

    private fun changeStateToInfo() = with(state.value) {
        copy(
            layoutState = DetailsState.LayoutState.Info
        ).postState()
    }

    private fun changeStateToCloseInfo() = with(state.value) {
        when (selectedButton) {
            DetailsState.SelectedButton.Original -> changeStateToOriginal()
            DetailsState.SelectedButton.GreyScale -> changeStateToGrayScale()
            DetailsState.SelectedButton.Blur -> changeStateToBlur()
        }
    }

    private fun initiateShare() = with(state.value) {
        viewModelScope.launch {
            info?.url?.also { _navigationFlow.emit(DetailsDestination.Share(it)) }
        }
    }

    private fun DetailsState.postState() {
        viewModelScope.launch {
            _state.emit(this@postState)
        }
    }

    private fun initialState(): DetailsState = DetailsState(
        pictureUrl = picsumArg.url,
        selectedButton = DetailsState.SelectedButton.Original,
        blurValue = BlurValue(1),
        layoutState = DetailsState.LayoutState.Init,
        info = null,
    )
}