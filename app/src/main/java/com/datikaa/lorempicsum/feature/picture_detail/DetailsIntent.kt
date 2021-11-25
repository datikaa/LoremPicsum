package com.datikaa.lorempicsum.feature.picture_detail

sealed class DetailsIntent {
    object Original : DetailsIntent()
    object GrayScale : DetailsIntent()
    object Blur : DetailsIntent()
    data class BlurValueChange(val value: Int) : DetailsIntent()
}