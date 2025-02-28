package com.project.nutriai.extensions.flow

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Deprecated(
    message = "Should use Flow<T>.collectInViewLifecycle",
    replaceWith = ReplaceWith(
        "this.collectInViewLifecycle(owner, minActiveState, action)"
    ),
    level = DeprecationLevel.WARNING,
)
inline fun <T> Flow<T>.collectIn(
    owner: Fragment,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline action: suspend (value: T) -> Unit,
) = collectIn(this as LifecycleOwner, minActiveState, action)

inline fun <T> Flow<T>.collectIn(
    owner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline action: suspend (value: T) -> Unit,
): Job = owner.lifecycleScope.launch {
    owner.repeatOnLifecycle(state = minActiveState) { collect { action(it) } }
}

/**
 * Launches a new coroutine and repeats `block` every time the Fragment's viewLifecycleOwner
 * is in and out of `minActiveState` lifecycle state.
 */
inline fun <T> Flow<T>.collectInViewLifecycle(
    fragment: Fragment,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline action: suspend (value: T) -> Unit,
): Job = collectIn(
    owner = fragment.viewLifecycleOwner,
    minActiveState = minActiveState,
    action = action,
)
