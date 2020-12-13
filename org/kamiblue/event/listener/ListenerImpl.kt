package org.kamiblue.event.listener

import org.kamiblue.event.ListenerManager


/**
 * Default priority for listeners
 */
const val DEFAULT_PRIORITY = 0

/**
 * Create and register a new async listener for this object
 *
 * @param T class of the target event
 * @param priority priority of this listener when calling by event bus
 * @param function action to perform when this listener gets called by event bus
 */
inline fun <reified T : Any> Any.asyncListener(priority: Int = DEFAULT_PRIORITY, noinline function: suspend (T) -> Unit) {
    ListenerManager.register(this, AsyncListener(T::class.java, priority, function))
}

/**
 * Create and register a new listener for this object
 *
 * @param T class of the target event
 * @param priority priority of this listener when calling by event bus
 * @param function action to perform when this listener gets called by event bus
 */
inline fun <reified T : Any> Any.listener(priority: Int = DEFAULT_PRIORITY, noinline function: (T) -> Unit) {
    ListenerManager.register(this, Listener(T::class.java, priority, function))
}

/**
 * Implementation of [AbstractListener] with suspend block
 */
class AsyncListener<T : Any>(
    override val eventClass: Class<T>,
    override val priority: Int,
    override val function: suspend (T) -> Unit
) : AbstractListener<T, suspend (T) -> Unit>()

/**
 * Basic implementation of [AbstractListener]
 */
class Listener<T : Any>(
    override val eventClass: Class<T>,
    override val priority: Int,
    override val function: (T) -> Unit
) : AbstractListener<T, (T) -> Unit>()


