package com.datikaa.lorempicsum.feature.picture_detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailsFragmentPicsumArg(
    val id: Int,
    val downloadUrl: String,
): Parcelable
