package com.datikaa.lorempicsum.feature.picture_detail.dynamics

import com.datikaa.lorempicsum.feature.picture_detail.tools.BlurValue

data class DetailsState(
    val pictureUrl: String,
    val selectedButton: SelectedButton,
    val blurValue: BlurValue,
    val layoutState: LayoutState,
    val info: Info?
) {

    val intBlurValue: Int
        get() = blurValue.value

    enum class SelectedButton {
        Original,
        GreyScale,
        Blur
    }

    enum class LayoutState {
        Init,
        NoBlur,
        ShowBlur,
        Info,
    }

    data class Info(
        val id: String?,
        val author: String?,
        val width: String?,
        val height: String?,
        val url: String?,
        val downloadUrl: String?,
    )
}


