package com.datikaa.lorempicsum.feature.main_pager.dynamics

import com.datikaa.lorempicsum.domain.data.PicsumPicture

sealed class MainPagerDestination {
    data class Details(val picsumPicture: PicsumPicture) : MainPagerDestination()
}
