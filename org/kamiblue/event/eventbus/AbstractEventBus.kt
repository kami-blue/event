package org.kamiblue.event.eventbus

import org.kamiblue.event.ListenerManager
import org.kamiblue.event.listener.Listener

abstract class AbstractEventBus : IEventBus {

    final override fun subscribe(vararg objects: Any) {
        for (`object` in objects) subscribe(`object`)
    }

    final override fun subscribe(objects: Iterable<Any>) {
        for (`object` in objects) subscribe(`object`)
    }

    override fun subscribe(`object`: Any) {
        ListenerManager.getListeners(`object`)?.let {
            subscribedObjects.getOrPut(`object`, ::newSet).addAll(it)
            for (listener in it) subscribedListeners.getOrPut(listener.eventClass, ::newSet).add(listener)
        }
    }


    final override fun unsubscribe(objects: Iterable<Any>) {
        for (`object` in objects) unsubscribe(`object`)
    }

    final override fun unsubscribe(vararg objects: Any) {
        for (`object` in objects) unsubscribe(`object`)
    }

    override fun unsubscribe(`object`: Any) {
        subscribedObjects.remove(`object`)?.forEach {
            subscribedListeners[it.eventClass]?.remove(it)
        }
    }


    final override fun post(vararg events: Any) {
        for (event in events) post(event)
    }

    final override fun post(events: Iterable<Any>) {
        for (event in events) post(event)
    }

    override fun post(event: Any) {
        subscribedListeners[event.javaClass]?.let {
            @Suppress("UNCHECKED_CAST") // IDE meme
            for (listener in it) (listener as Listener<Any>).function.invoke(event)
        }
    }

}