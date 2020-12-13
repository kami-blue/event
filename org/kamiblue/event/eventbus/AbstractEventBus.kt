package org.kamiblue.event.eventbus

import org.kamiblue.event.ListenerManager
import org.kamiblue.event.listener.Listener

abstract class AbstractEventBus : IEventBus {

    /**
     * A map for subscribed objects and their listeners
     *
     * <SubscribedObject, Set<Listener>>
     */
    protected abstract val subscribedObjects: MutableMap<Any, MutableSet<Listener<*>>>

    /**
     * A map for events and their subscribed listeners
     *
     * <Event, Set<Listener>>
     */
    protected abstract val subscribedListeners: MutableMap<Class<*>, MutableSet<Listener<*>>>

    /**
     * Called when putting a new set to the map
     */
    protected abstract val newSet: MutableSet<Listener<*>>


    override fun subscribe(vararg objects: Any) {
        for (`object` in objects) subscribe(`object`)
    }

    override fun subscribe(objects: Iterable<Any>) {
        for (`object` in objects) subscribe(`object`)
    }

    override fun subscribe(`object`: Any) {
        ListenerManager.getListeners(`object`)?.let {
            subscribedObjects.getOrPut(`object`, ::newSet).addAll(it)
            for (listener in it) subscribedListeners.getOrPut(listener.eventClass, ::newSet).add(listener)
        }
    }


    override fun unsubscribe(objects: Iterable<Any>) {
        for (`object` in objects) unsubscribe(`object`)
    }

    override fun unsubscribe(vararg objects: Any) {
        for (`object` in objects) unsubscribe(`object`)
    }

    override fun unsubscribe(`object`: Any) {
        subscribedObjects.remove(`object`)?.also { for (listener in it) subscribedListeners[listener.eventClass]?.remove(listener) }
    }


    override fun post(vararg events: Any) {
        for (event in events) post(event)
    }

    override fun post(events: Iterable<Any>) {
        for (event in events) post(event)
    }

    override fun post(event: Any) {
        subscribedListeners[event.javaClass]?.let {
            @Suppress("UNCHECKED_CAST") // IDE meme
            for (listener in it) (listener as Listener<Any>).function.invoke(event)
        }
    }

}