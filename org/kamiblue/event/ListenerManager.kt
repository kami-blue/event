package org.kamiblue.event

import org.kamiblue.event.listener.AsyncListener
import org.kamiblue.event.listener.Listener
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Used for storing the map of objects and their listeners
 */
object ListenerManager {

    private val listenerMap = ConcurrentHashMap<Any, CopyOnWriteArrayList<Listener<*>>>()

    private val asyncListenerMap = ConcurrentHashMap<Any, CopyOnWriteArrayList<AsyncListener<*>>>()

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
     * Register the [asyncListener] to the [ListenerManager]
     *
     * @param object object of the [asyncListener] belongs to
     * @param asyncListener async listener to register
     */
    fun register(`object`: Any, asyncListener: AsyncListener<*>) {
        asyncListenerMap.getOrPut(`object`, ::CopyOnWriteArrayList).add(asyncListener)
    }

    /**
     * Get all registered listeners of this [object]
     *
     * @param object object to get listeners
     *
     * @return registered listeners of [object]
     */
    fun getListeners(`object`: Any): List<Listener<*>>? = listenerMap[`object`]

    /**
     * Get all registered async listeners of this [object]
     *
     * @param object object to get async listeners
     *
     * @return registered async listeners of [object]
     */
    fun getAsyncListeners(`object`: Any): List<AsyncListener<*>>? = asyncListenerMap[`object`]

}