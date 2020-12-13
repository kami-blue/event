package org.kamiblue.event.eventbus

import org.kamiblue.event.listener.Listener

/**
 * The basic Interface for an event bus
 */
interface IEventBus {

    /**
     * A map for subscribed objects and their listeners
     *
     * <SubscribedObject, Set<Listener>>
     */
    val subscribedObjects: MutableMap<Any, MutableSet<Listener<*>>>

    /**
     * A map for events and their subscribed listeners
     *
     * <Event, Set<Listener>>
     */
    val subscribedListeners: MutableMap<Class<*>, MutableSet<Listener<*>>>

    /**
     * Called when putting a new set to the map
     */
    val newSet: MutableSet<Listener<*>>

    /**
     * Subscribes all [objects]' listeners to this event bus
     */
    fun subscribe(vararg objects: Any)

    /**
     * Subscribes all [objects]' listeners to this event bus
     */
    fun subscribe(objects: Iterable<Any>)

    /**
     * Subscribe an [object]'s listeners to this event bus
     */
    fun subscribe(`object`: Any)


    /**
     * unsubscribes all [objects]' listeners from this event bus
     */
    fun unsubscribe(vararg objects: Any)

    /**
     * unsubscribes all [objects]' listeners from this event bus
     */
    fun unsubscribe(objects: Iterable<Any>)

    /**
     * unsubscribes an [object]'s listeners from this event bus
     */
    fun unsubscribe(`object`: Any)


    /**
     * Posts all events to this event bus, and calls
     * All the listeners of each event
     */
    fun post(vararg events: Any)

    /**
     * Posts all events to this event bus, and calls
     * All the listeners of each event
     */
    fun post(events: Iterable<Any>)

    /**
     * Posts an event to this event bus, and calls
     * All the listeners of this event
     */
    fun post(event: Any)
}