package org.kamiblue.event.eventbus

import org.kamiblue.event.ListenerManager

/**
 * [IAsyncEventBus] with some basic implementation
 * Must be used with Kotlinx Coroutine and overridden [post] method
 */
abstract class AbstractAsyncEventBus : AbstractEventBus(), IAsyncEventBus {

    override fun subscribe(`object`: Any) {
        super.subscribe(`object`)
        ListenerManager.getAsyncListeners(`object`)?.let {
            subscribedObjectsAsync.getOrPut(`object`, ::newSetAsync).addAll(it)
            for (listener in it) subscribedListenersAsync.getOrPut(listener.eventClass, ::newSetAsync).add(listener)
        }
    }

    override fun unsubscribe(`object`: Any) {
        super.unsubscribe(`object`)
        subscribedObjectsAsync.remove(`object`)?.forEach {
            subscribedListenersAsync[it.eventClass]?.remove(it)
        }
    }

}