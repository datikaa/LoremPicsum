package com.datikaa.lorempicsum.feature.main_pager.paging

data class MainPagerItemModel(
    val id: Int,
    val width: Int,
    val height: Int,
    val downloadUrl: String,
)

val MainPagerItemModel.url: String
    get() = "https://picsum.photos/id/$id/$width/$height"
