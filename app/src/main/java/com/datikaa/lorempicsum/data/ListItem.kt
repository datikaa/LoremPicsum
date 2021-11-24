package com.datikaa.lorempicsum.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListItem(
    val id: Int,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    val download_url: String,
)
