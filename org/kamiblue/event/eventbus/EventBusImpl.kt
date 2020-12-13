package org.kamiblue.event.eventbus

import io.netty.util.internal.ConcurrentSet
import me.zeroeightsix.kami.util.event.EventBus
import org.kamiblue.event.listener.Listener
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentSkipListSet


/**
 * A concurrent implementation of [AbstractEventBus]
 */
open class EventBusImpl : AbstractEventBus() {
    final override val subscribedObjects = ConcurrentHashMap<Any, MutableSet<Listener<*>>>()
    final override val subscribedListeners = ConcurrentHashMap<Class<*>, MutableSet<Listener<*>>>()
    final override val newSet get() = ConcurrentSkipListSet<Listener<*>>(Comparator.reverseOrder())
}

/**
 * A concurrent implementation of [AbstractEventBus] and [IMultiEventBus]
 */
open class MasterEventBus : EventBus.ConcurrentEventBus(), IMultiEventBus {
    private val subscribedEventBus = ConcurrentSet<IEventBus>()

    final override fun subscribe(eventBus: IEventBus) {
        subscribedEventBus.add(eventBus)
    }

    final override fun unsubscribe(eventBus: IEventBus) {
        subscribedEventBus.remove(eventBus)
    }

    final override fun post(event: Any) {
        super.post(event)
        for (eventBus in subscribedEventBus) eventBus.post(event)
    }
}