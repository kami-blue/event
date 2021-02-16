package org.kamiblue.event.eventbus

import org.kamiblue.event.listener.AsyncListener

interface IAsyncEventBus : IEventBus {

    /**
     * A map for subscribed objects and their listeners
     *
     * <SubscribedObject, List<Listener>>
     */
    val subscribedObjectsAsync: MutableMap<Any, List<AsyncListener<*>>>

    /**
     * A map for events and their subscribed listeners
     *
     * <Event, Set<Listener>>
     */
    val subscribedListenersAsync: MutableMap<Class<*>, MutableSet<AsyncListener<*>>>

    /**
     * Called when putting a new set to the map
     */
    val newSetAsync: MutableSet<AsyncListener<*>>

}