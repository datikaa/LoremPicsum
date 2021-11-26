package com.datikaa.lorempicsum.feature.picture_detail.dynamics

import com.datikaa.lorempicsum.feature.picture_detail.tools.BlurValue

data class DetailsState(
    val pictureUrl: String,
    val selectedButton: SelectedButton,
    val blurValue: BlurValue,
) {

    fun getBlurValueInt() = blurValue.value

    enum class SelectedButton {
        Original,
        GreyScale,
        Blur
    }
}


