package com.datikaa.lorempicsum.feature.picture_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.datikaa.lorempicsum.feature.picture_detail.dynamics.DetailsDestination
import com.datikaa.lorempicsum.feature.picture_detail.dynamics.DetailsIntent
import com.datikaa.lorempicsum.feature.picture_detail.dynamics.DetailsState
import com.datikaa.lorempicsum.feature.picture_detail.tools.DetailsFragmentPicsumArg
import com.datikaa.lorempicsum.network.PicsumService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val picsumService: PicsumService,
    private val picsumArg: DetailsFragmentPicsumArg,
) : ViewModel() {

    private val _navigationFlow = MutableSharedFlow<DetailsDestination>(replay = 0)
    val navigationFlow: SharedFlow<DetailsDestination> = _navigationFlow

    private val _state: MutableStateFlow<DetailsState>
    val state: StateFlow<DetailsState>

    init {
        // Grab saved state if exists else create new initialState
        val initState = savedStateHandle["state"] ?: initialState()
        _state = MutableStateFlow(initState)
        state = _state
            .onEach { savedStateHandle["state"] = it } // save state
            .stateIn(viewModelScope, SharingStarted.Eagerly, initState)
        if (initState.layoutState == DetailsState.LayoutState.Init) {
            viewModelScope.launch {
                delay(50) // just to have a little time for the initState to work properly
                state.value.copy(
                    pictureUrl = picsumArg.downloadUrl,
                    layoutState = DetailsState.LayoutState.NoBlur,
                ).postState()
                // fetch specific info for given id and post new state if success
                val picsumResponseItem = failSafe {
                    picsumService.info(picsumArg.id)
                } ?: return@launch
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
            pictureUrl = "${picsumArg.downloadUrl}?blur=${blurValue}",
            layoutState = DetailsState.LayoutState.ShowBlur,
        ).postState()
    }

    private fun changeStateBlurValue(blurValue: Int) = with(state.value) {
        if (this.blurValue == blurValue) return@with
        copy(
            blurValue = blurValue,
            selectedButton = DetailsState.SelectedButton.Blur,
            pictureUrl = "${picsumArg.downloadUrl}?blur=${blurValue}",
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
        blurValue = 1,
        layoutState = DetailsState.LayoutState.Init,
        info = null,
    )

    private suspend fun <T> failSafe(action: suspend () -> T): T? = try {
        action()
    } catch (e: Exception) {
        null
    }
}