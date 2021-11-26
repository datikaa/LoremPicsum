package com.datikaa.lorempicsum.feature.picture_detail.dynamics

import com.datikaa.lorempicsum.feature.picture_detail.tools.BlurValue

sealed class DetailsIntent {
    object Original : DetailsIntent()
    object GrayScale : DetailsIntent()
    object Blur : DetailsIntent()
    data class BlurValueChange(val blurValue: BlurValue) : DetailsIntent()
}