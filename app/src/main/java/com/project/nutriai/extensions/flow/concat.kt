package com.project.nutriai.extensions.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

/**
 * Creates an output [Flow] which sequentially emits all values from the first given [Flow] and then moves on to the next.
 */
fun <T> concat(flow1: Flow<T>, flow2: Flow<T>): Flow<T> = flow {
    emitAll(flow1)
    emitAll(flow2)
}

/**
 * Creates an output [Flow] which sequentially emits all values from the first given [Flow] and then moves on to the next.
 */
fun <T> concat(flow1: Flow<T>, flow2: Flow<T>, flow3: Flow<T>): Flow<T> = flow {
    emitAll(flow1)
    emitAll(flow2)
    emitAll(flow3)
}

/**
 * Creates an output [Flow] which sequentially emits all values from the first given [Flow] and then moves on to the next.
 */
fun <T> concat(
    flow1: Flow<T>,
    flow2: Flow<T>,
    flow3: Flow<T>,
    flow4: Flow<T>,
): Flow<T> =
    flow {
        emitAll(flow1)
        emitAll(flow2)
        emitAll(flow3)
        emitAll(flow4)
    }

/**
 * Creates an output [Flow] which sequentially emits all values from the first given [Flow] and then moves on to the next.
 */
fun <T> concat(
    flow1: Flow<T>,
    flow2: Flow<T>,
    flow3: Flow<T>,
    flow4: Flow<T>,
    flow5: Flow<T>,
): Flow<T> =
    flow {
        emitAll(flow1)
        emitAll(flow2)
        emitAll(flow3)
        emitAll(flow4)
        emitAll(flow5)
    }

/**
 * Creates an output [Flow] which sequentially emits all values from the first given [Flow] and then moves on to the next.
 */
fun <T> concat(flow1: Flow<T>, flow2: Flow<T>, vararg flows: Flow<T>): Flow<T> = flow {
    emitAll(flow1)
    emitAll(flow2)
    flows.forEach {
        emitAll(it)
    }
}

/**
 * Creates an output [Flow] which sequentially emits all values from the first given [Flow] and then moves on to the next.
 */
fun <T> concat(flows: Iterable<Flow<T>>): Flow<T> {
    return flow { flows.forEach { emitAll(it) } }
}

/**
 * Creates an output [Flow] which sequentially emits all values from the first given [Flow] and then moves on to the next.
 */
fun <T> concat(flows: Sequence<Flow<T>>): Flow<T> {
    return flow { flows.forEach { emitAll(it) } }
}

//
// concatWith
//

/**
 * Returns a [Flow] that emits the items emitted from the current [Flow], then the next, one after the other, without interleaving them.
 */
fun <T> Flow<T>.concatWith(flow: Flow<T>): Flow<T> = concat(this, flow)

/**
 * Returns a [Flow] that emits the items emitted from the current [Flow], then the next, one after the other, without interleaving them.
 */
fun <T> Flow<T>.concatWith(flow1: Flow<T>, flow2: Flow<T>): Flow<T> =
    concat(this, flow1, flow2)

/**
 * Returns a [Flow] that emits the items emitted from the current [Flow], then the next, one after the other, without interleaving them.
 */
fun <T> Flow<T>.concatWith(flow1: Flow<T>, flow2: Flow<T>, flow3: Flow<T>): Flow<T> =
    concat(this, flow1, flow2, flow3)

/**
 * Returns a [Flow] that emits the items emitted from the current [Flow], then the next, one after the other, without interleaving them.
 */
fun <T> Flow<T>.concatWith(
    flow1: Flow<T>,
    flow2: Flow<T>,
    flow3: Flow<T>,
    flow4: Flow<T>,
): Flow<T> =
    concat(this, flow1, flow2, flow3, flow4)

/**
 * Returns a [Flow] that emits the items emitted from the current [Flow], then the next, one after the other, without interleaving them.
 */
fun <T> Flow<T>.concatWith(flow: Flow<T>, vararg others: Flow<T>): Flow<T> =
    concat(this, flow, *others)

/**
 * Returns a [Flow] that emits the items emitted from the current [Flow], then the next, one after the other, without interleaving them.
 */
fun <T> Flow<T>.concatWith(others: Iterable<Flow<T>>): Flow<T> {
    return flow {
        emitAll(this@concatWith)
        others.forEach { emitAll(it) }
    }
}

/**
 * Returns a [Flow] that emits the items emitted from the current [Flow], then the next, one after the other, without interleaving them.
 */
fun <T> Flow<T>.concatWith(others: Sequence<Flow<T>>): Flow<T> {
    return flow {
        emitAll(this@concatWith)
        others.forEach { emitAll(it) }
    }
}

//
// startWith
//

/**
 * Returns a [Flow] that emits a specified item before it begins to emit items emitted by the current [Flow].
 */
fun <T> Flow<T>.startWith(item: T): Flow<T> = concat(flowOf(item), this)

/**
 * Returns a [Flow] that emits a specified item before it begins to emit items emitted by the current [Flow].
 * [itemFactory] will be called on the collection for each new [FlowCollector].
 */
fun <T> Flow<T>.startWith(itemFactory: suspend () -> T): Flow<T> = concat(
    flow { emit(itemFactory()) },
    this,
)

/**
 * Returns a [Flow] that emits the specified items before it begins to emit items emitted by the current [Flow].
 */
fun <T> Flow<T>.startWith(item1: T, item2: T): Flow<T> = concat(
    flow {
        emit(item1)
        emit(item2)
    },
    this,
)

/**
 * Returns a [Flow] that emits the specified items before it begins to emit items emitted by the current [Flow].
 */
fun <T> Flow<T>.startWith(item1: T, item2: T, item3: T): Flow<T> = concat(
    flow {
        emit(item1)
        emit(item2)
        emit(item3)
    },
    this,
)

/**
 * Returns a [Flow] that emits the specified items before it begins to emit items emitted by the current [Flow].
 */
fun <T> Flow<T>.startWith(item1: T, item2: T, item3: T, item4: T): Flow<T> = concat(
    flow {
        emit(item1)
        emit(item2)
        emit(item3)
        emit(item4)
    },
    this,
)

/**
 * Returns a [Flow] that emits the specified items before it begins to emit items emitted by the current [Flow].
 */
fun <T> Flow<T>.startWith(item1: T, item2: T, item3: T, item4: T, item5: T): Flow<T> =
    concat(
        flow {
            emit(item1)
            emit(item2)
            emit(item3)
            emit(item4)
            emit(item5)
        },
        this,
    )

/**
 * Returns a [Flow] that emits the specified items before it begins to emit items emitted by the current [Flow].
 */
fun <T> Flow<T>.startWith(item: T, vararg items: T): Flow<T> {
    return concat(
        flow {
            emit(item)
            items.forEach {
                emit(it)
            }
        },
        this,
    )
}

/**
 * Returns a [Flow] that emits the specified items before it begins to emit items emitted by the current [Flow].
 */
fun <T> Flow<T>.startWith(others: Iterable<T>): Flow<T> = concat(others.asFlow(), this)

/**
 * Returns a [Flow] that emits the specified items before it begins to emit items emitted by the current [Flow].
 */
fun <T> Flow<T>.startWith(others: Sequence<T>): Flow<T> = concat(others.asFlow(), this)

/**
 * Returns a [Flow] that emits the items in a specified [Flow] before it begins to emit items emitted by the current [Flow].
 */
fun <T> Flow<T>.startWith(other: Flow<T>): Flow<T> = concat(other, this)
