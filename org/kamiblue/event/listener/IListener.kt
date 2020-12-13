package org.kamiblue.event.listener

interface IListener<E : Any, F> : Comparable<IListener<*, *>> {

    /**
     * Class of the target event
     */
    val eventClass: Class<E>

    /**
     * Priority of this listener when calling by event bus
     */
    val priority: Int

    /**
     * Action to perform when this listener gets called by event bus
     */
    val function: F

    /**
     * An unique id for a listener
     */
    val id: Int

}