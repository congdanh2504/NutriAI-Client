package com.project.nutriai.extensions.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration

/**
 * Creates a [Flow] that will wait for a specified time, before emitting the [value].
 */
fun <T> timer(value: T, timeMillis: Long): Flow<T> = flow {
    delay(timeMillis)
    emit(value)
}

/**
 * Creates a [Flow] that will wait for a given [duration], before emitting the [value].
 */
fun <T> timer(value: T, duration: Duration): Flow<T> = flow {
    delay(duration)
    emit(value)
}