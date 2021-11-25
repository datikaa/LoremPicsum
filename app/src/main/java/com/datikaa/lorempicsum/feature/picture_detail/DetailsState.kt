package com.datikaa.lorempicsum.feature.picture_detail

data class DetailsState(
    val pictureUrl: String,
    val selectedButton: SelectedButton,
    val blurValue: BlurValue?,
) {
    enum class SelectedButton {
        Original,
        GreyScale,
        Blur
    }
}

