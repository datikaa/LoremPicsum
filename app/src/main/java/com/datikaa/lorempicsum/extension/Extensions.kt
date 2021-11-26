package com.datikaa.lorempicsum.extension

import com.datikaa.lorempicsum.domain.data.PicsumPicture
import com.datikaa.lorempicsum.feature.main_pager.paging.MainPagerItemModel
import com.datikaa.lorempicsum.feature.picture_detail.tools.BlurValue
import com.datikaa.lorempicsum.network.response.PicsumResponseItem

/**
 * Converts [PicsumResponseItem] to [PicsumPicture]
 */
fun PicsumResponseItem.toDomainModel() = PicsumPicture(
    id = id,
    author = author,
    width = width,
    height = height,
    url = url,
    downloadUrl = downloadUrl
)

/**
 * Converts [PicsumPicture] to [MainPagerItemModel]
 */
fun PicsumPicture.toAdapterItem() = MainPagerItemModel(
    id = id,
    width = width,
    height = height,
    downloadUrl = downloadUrl,
)

/**
 * Recursively calculate image size by reducing it in 10% increments
 */
fun PicsumPicture.calculateSize(): PicsumPicture {
    return if (width > screenMetrics.width / 2) {
        copy(
            width = (width / 1.1).toInt(),
            height = (height / 1.1).toInt(),
        ).calculateSize()
    } else this
}

val PicsumPicture.calculatedUrl: String
    get() = "https://picsum.photos/id/$id/$width/$height"

fun Float.toBlurValue() = BlurValue(this.toInt())