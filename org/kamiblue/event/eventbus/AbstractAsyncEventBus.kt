package org.kamiblue.event.eventbus

import org.kamiblue.event.ListenerManager

/**
 * [IAsyncEventBus] with some basic implementation
 * Must be used with Kotlinx Coroutine and overridden [post] method
 */
abstract class AbstractAsyncEventBus : AbstractEventBus(), IAsyncEventBus {

    override fun subscribe(`object`: Any) {
        if (subscribedObjects.containsKey(`object`)) return

        super.subscribe(`object`)
        ListenerManager.getAsyncListeners(`object`)?.let {
            subscribedObjectsAsync[`object`] = it
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