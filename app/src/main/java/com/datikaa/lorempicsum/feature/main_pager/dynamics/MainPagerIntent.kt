package com.datikaa.lorempicsum.feature.main_pager.dynamics

import com.datikaa.lorempicsum.domain.data.PicsumPicture

sealed class MainPagerIntent {
    data class ShowDetails(val picsumPicture: PicsumPicture) : MainPagerIntent()
}
