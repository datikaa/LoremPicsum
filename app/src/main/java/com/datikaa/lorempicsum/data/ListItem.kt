package com.datikaa.lorempicsum.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListItem(
    @Json(name = "id") val id: Int,
    @Json(name = "author") val author: String,
    @Json(name = "width") val width: Int,
    @Json(name = "height") val height: Int,
    @Json(name = "url") val url: String,
    @Json(name = "download_url") val downloadUrl: String,
)
