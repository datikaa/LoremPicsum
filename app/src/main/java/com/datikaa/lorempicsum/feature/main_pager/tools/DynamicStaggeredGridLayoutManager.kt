package com.datikaa.lorempicsum.feature.main_pager.tools

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.datikaa.lorempicsum.extension.displayMetrics

/**
 * A [StaggeredGridLayoutManager] implementation that changes span count depending on screen width
 */
class DynamicStaggeredGridLayoutManager : StaggeredGridLayoutManager {

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    constructor() : super(2, RecyclerView.VERTICAL)

    init {
        orientation = RecyclerView.VERTICAL
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        spanCount = when {
            dpWidth > 840 -> 3
            dpWidth > 600 -> 2
            else -> 1
        }
    }
}