package org.kamiblue.event.eventbus

/**
 * Event bus that allow subscribing another [IEventBus] to it
 */
interface IMultiEventBus {
    /**
     * Subscribe an [eventBus] to this event bus
     */
    fun subscribe(eventBus: IEventBus)


    /**
     * unsubscribes an [eventBus] from this event bus
     */
    fun unsubscribe(eventBus: IEventBus)
}