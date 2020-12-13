package org.kamiblue.event.listener

abstract class AbstractListener<E : Any, F> : IListener<E, F> {

    final override val id: Int = listenerId++

    override fun compareTo(other: IListener<*, *>): Int {
        val result = priority - other.priority
        return if (result != 0) result
        else id.compareTo(other.id) // :monkey: code for getting around TreeSet duplicated check
    }

    override fun equals(other: Any?): Boolean {
        return this === other
            || (other is IListener<*, *>
            && other.eventClass == this.eventClass
            && other.id == this.id)
    }

    override fun hashCode(): Int {
        return 31 * eventClass.hashCode() + id.hashCode()
    }

    companion object {
        private var listenerId = Int.MIN_VALUE
    }

}