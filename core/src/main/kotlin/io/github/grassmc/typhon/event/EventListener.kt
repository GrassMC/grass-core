package io.github.grassmc.typhon.event

import io.github.grassmc.typhon.plugin.firstJavaPlugin
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin

/**
 * Represents an event listener for a specific event type [E].
 *
 * @param E The event type to listen for.
 */
fun interface EventListener<E : Event> : Listener {
    /** Executes a callback when the specified [event] occurs. */
    fun on(event: E)
}

/**
 * Registers this event listener for the specified event type [E].
 *
 * @param E The event type to listen for.
 */
inline fun <reified E : Event> EventListener<E>.register(
    plugin: Plugin = firstJavaPlugin(),
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
) = register<E>(plugin, priority, ignoreCancelled) { _, event -> on(event) }
