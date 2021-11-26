package com.datikaa.lorempicsum.feature.main_pager.dynamics

import com.datikaa.lorempicsum.domain.data.PicsumPicture

sealed class MainPagerNavigation {
    data class ToDetails(val picsumPicture: PicsumPicture) : MainPagerNavigation()
}
