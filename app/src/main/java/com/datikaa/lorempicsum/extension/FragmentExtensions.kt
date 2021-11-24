package com.datikaa.lorempicsum.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
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