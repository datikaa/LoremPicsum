package com.datikaa.lorempicsum.extension

import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.datikaa.lorempicsum.LoremPicsumActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Shorthand for [Flow.flowWithLifecycle] API.
 *
 * Uses the [Lifecycle][androidx.lifecycle.Lifecycle] of the Fragment's [View][android.view.View]
 * via [viewLifecycleOwner][Fragment.getViewLifecycleOwner]
 */
fun <T> Fragment.onEachWithLifecycle(
    flow: Flow<T>,
    lifecycle: Lifecycle = this.viewLifecycleOwner.lifecycle,
    lifecycleScope: LifecycleCoroutineScope = this.lifecycleScope,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    action: suspend (value: T) -> Unit

) {
    flow
        .flowWithLifecycle(lifecycle, minActiveState)
        .onEach(action)
        .launchIn(lifecycleScope)
}

val Fragment.loremPicsumActivity: LoremPicsumActivity
    get() = this.activity as LoremPicsumActivity

fun Fragment.postponeEnterTransitionUntilLaidOut(view: View) {
    postponeEnterTransition()
    view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            view.viewTreeObserver.removeOnGlobalLayoutListener(this)
            startPostponedEnterTransition()
        }
    })
}


