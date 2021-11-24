package com.datikaa.lorempicsum.extension

import android.content.res.Resources
import android.util.DisplayMetrics
import com.datikaa.lorempicsum.ScreenMetrics
import com.datikaa.lorempicsum.toScreenMetrics

val displayMetrics: DisplayMetrics
    get() = Resources.getSystem().displayMetrics

val screenMetrics: ScreenMetrics
    get() = Resources.getSystem().displayMetrics.toScreenMetrics()
