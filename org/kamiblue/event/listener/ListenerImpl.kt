package org.kamiblue.event.listener

import org.kamiblue.event.ListenerManager
import org.kamiblue.event.eventbus.IAsyncEventBus


/**
 * Default priority for listeners
 */
const val DEFAULT_PRIORITY = 0

/**
 * Create and register a new async listener for this object
 * Must be used with Kotlinx Coroutine and a implementation of [IAsyncEventBus]
 *
 * @param T class of the target event
 * @param priority priority of this listener when calling by event bus
 * @param function action to perform when this listener gets called by event bus
 */
inline fun <reified T : Any> Any.asyncListener(noinline function: suspend (T) -> Unit) {
    ListenerManager.register(this, AsyncListener(T::class.java, function))
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
 * Must be used with Kotlinx Coroutine and a implementation of [IAsyncEventBus]
 */
class AsyncListener<T : Any>(
    override val eventClass: Class<T>,
    override val function: suspend (T) -> Unit
) : AbstractListener<T, suspend (T) -> Unit>() {
    override val priority: Int = DEFAULT_PRIORITY
}

/**
 * Basic implementation of [AbstractListener]
 */
class Listener<T : Any>(
    override val eventClass: Class<T>,
    override val priority: Int,
    override val function: (T) -> Unit
) : AbstractListener<T, (T) -> Unit>()


