package com.datikaa.lorempicsum.feature.picture_detail.dynamics

sealed class DetailsDestination {
    data class Share(val url: String) : DetailsDestination()
}
