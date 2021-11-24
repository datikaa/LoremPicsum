package com.datikaa.lorempicsum.extension

import com.datikaa.lorempicsum.domain.data.PicsumPicture
import com.datikaa.lorempicsum.feature.main_pager.model.MainItemModel
import com.datikaa.lorempicsum.network.response.PicsumResponseItem

fun PicsumResponseItem.toDomainModel() = PicsumPicture(
    id = id,
    author = author,
    width = width,
    height = height,
    url = url,
    downloadUrl = downloadUrl
)

fun PicsumPicture?.toAdapterItem() = MainItemModel(
    id = this?.id,
    url = this?.downloadUrl,
)