package com.datikaa.lorempicsum.extension

import com.datikaa.lorempicsum.domain.data.PicsumPicture
import com.datikaa.lorempicsum.feature.main_pager.model.MainPagerItemModel
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

fun Float.toBlurValue() = BlurValue(this.toInt())