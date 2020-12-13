package org.kamiblue.event

/**
 * Listener for event listening
 *
 * @param function action to perform when this listener gets called by the event bus
 */
class Listener<T : Any>(
    val event: Class<T>,
    val priority: Int,
    private val function: (T) -> Unit
) : Comparable<Listener<*>> {

    private val id = listenerId++

    fun invoke(event: T) = function(event)

    override fun compareTo(other: Listener<*>): Int {
        val result = priority - other.priority
        return if (result != 0) result
        else id.compareTo(other.id) // :monkey: code for getting around TreeSet duplicated check
    }

    override fun equals(other: Any?) = this === other
        || (other is Listener<*>
        && other.event == this.event
        && other.function == this.function)

    override fun hashCode(): Int {
        return 31 * event.hashCode() + function.hashCode()
    }

    companion object {
        private var listenerId = Int.MIN_VALUE
    }

}


/**
 * Default priority for listeners
 */
const val DEFAULT_PRIORITY = 0


/**
 * Create and register a new listener for this object
 *
 * @param T target event
 * @param priority Priority of this listener when calling by event bus
 * @param function action to perform when this listener gets called by event bus
 */
inline fun <reified T : Any> Any.listener(priority: Int = DEFAULT_PRIORITY, noinline function: (event: T) -> Unit) {
    ListenerManager.register(this, Listener(T::class.java, priority, function))
}