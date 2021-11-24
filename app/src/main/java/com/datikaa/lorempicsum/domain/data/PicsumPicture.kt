package com.datikaa.lorempicsum.domain.data

data class PicsumPicture(
    val id: Int,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    val downloadUrl: String,
)
