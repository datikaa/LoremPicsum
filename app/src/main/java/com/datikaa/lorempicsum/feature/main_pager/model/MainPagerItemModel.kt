package com.datikaa.lorempicsum.feature.main_pager.model

data class MainPagerItemModel(
    val id: Int,
    val width: Int,
    val height: Int,
)

val MainPagerItemModel.url: String
    get() = "https://picsum.photos/id/$id/$width/$height"
