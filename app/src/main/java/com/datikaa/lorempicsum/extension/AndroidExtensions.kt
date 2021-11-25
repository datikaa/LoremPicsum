package com.datikaa.lorempicsum.extension

import android.view.View
import android.view.ViewTreeObserver
import androidx.annotation.AnimatorRes
import androidx.annotation.TransitionRes
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import com.datikaa.lorempicsum.LoremPicsumActivity
import com.google.android.material.slider.Slider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.*
import java.time.Duration

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

fun View.setCompatTransitionName(transitionName: String) {
    ViewCompat.setTransitionName(this, transitionName)
}

fun Fragment.inflateTransition(@TransitionRes resourceId: Int): Transition {
    return TransitionInflater.from(context).inflateTransition(resourceId)
}

data class SliderValueChange(
    val slider: Slider,
    val value: Float,
    val fromUser: Boolean
)

fun Slider.valueChangeFlow(duration: Long = 500) = callbackFlow {
    val listener = Slider.OnChangeListener { slider, value, fromUser ->
        trySendBlocking(SliderValueChange(slider, value, fromUser))
    }
    addOnChangeListener(listener)
    awaitClose {
        removeOnChangeListener(listener)
    }
}.debounce(duration)


