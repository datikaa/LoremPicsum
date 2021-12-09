package com.datikaa.lorempicsum.feature.picture_detail.dynamics

sealed class DetailsIntent {
    object Original : DetailsIntent()
    object GrayScale : DetailsIntent()
    object Blur : DetailsIntent()
    object Info : DetailsIntent()
    object CloseInfo : DetailsIntent()
    object Share : DetailsIntent()
    data class BlurValueChange(val blurValue: Int) : DetailsIntent()
}