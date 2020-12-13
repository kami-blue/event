package org.kamiblue.event

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Used for storing the map of objects and their listeners
 */
object ListenerManager {

    private val listenerMap = ConcurrentHashMap<Any, CopyOnWriteArrayList<Listener<*>>>()

    /**
     * Register the [listener] to the [ListenerManager]
     *
     * @param object object of the [listener] belongs to
     * @param listener listener to register
     */
    fun register(`object`: Any, listener: Listener<*>) {
        listenerMap.getOrPut(`object`, ::CopyOnWriteArrayList).add(listener)
    }

    /**
     * Get all registered listeners of this [object]
     *
     * @param object object to get listeners
     *
     * @return registered listeners of [object]
     */
    fun getListeners(`object`: Any) = listenerMap[`object`]

}