package com.datikaa.lorempicsum.feature.picture_detail.tools

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailsFragmentPicsumArg(
    val id: Int,
    val url: String,
    val downloadUrl: String,
): Parcelable
