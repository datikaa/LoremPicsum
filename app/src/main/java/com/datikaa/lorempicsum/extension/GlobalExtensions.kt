package com.datikaa.lorempicsum.extension

import android.content.res.Resources
import com.datikaa.lorempicsum.ScreenMetrics
import com.datikaa.lorempicsum.toScreenMetrics

val screenMetrics: ScreenMetrics
    get() = Resources.getSystem().displayMetrics.toScreenMetrics()
