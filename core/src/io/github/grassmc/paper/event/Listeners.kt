@file:Suppress("unused")

package io.github.grassmc.paper.event

import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import kotlin.coroutines.CoroutineContext

/**
 * Unregisters this listener from all event handler lists.
 *
 * @see HandlerList.unregisterAll
 */
fun Listener.unregister() {
    HandlerList.unregisterAll(this)
}

/**
 * Registers a listener [action] for a specific event type [E] in the context of a plugin.
 *
 * @param priority The priority of the event listener (default: `NORMAL`).
 * @param ignoreCancelled Determines whether canceled events should be ignored (default: `false`).
 * @param action The action to be executed when the event occurs.
 *
 * @return The event listener instance.
 *
 * @param E The event type to listen for.
 */
inline fun <reified E : Event> Plugin.listen(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    crossinline action: (E) -> Unit,
) = EventListener<E> { event -> action(event) }.also {
    it.register(this, priority, ignoreCancelled)
}

/**
 * Registers a suspending event listener [action] for a specific event type [E] in the context of a plugin.
 *
 * @param E The event type to listen for.
 * @param contextResolver A function that can resolve the coroutine context for the event.
 * @param priority The priority of the event listener (default: `NORMAL`).
 * @param ignoreCancelled Determines whether canceled events should be ignored (default: `false`).
 * @param action The suspending action to be executed when the event occurs.
 * @return The suspending event listener instance.
 */
inline fun <reified E : Event> Plugin.listenSuspending(
    noinline contextResolver: (E) -> CoroutineContext,
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    crossinline action: suspend (E) -> Unit,
) = SuspendingEventListener<E> { event -> action(event) }.also {
    it.register(this, priority, ignoreCancelled, contextResolver)
}
