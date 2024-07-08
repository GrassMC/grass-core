package io.github.grassmc.paper.event

import io.github.grassmc.typhon.server.PaperServer
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
    fun on(event: E)
}

/**
 * Registers this event listener for the specified event type [E].
 *
 * @param E The event type to listen for.
 */
inline fun <reified E : Event> EventListener<E>.register(
    plugin: Plugin,
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
) = PaperServer.pluginManager.registerEvent(
    E::class.java,
    this,
    priority,
    { _, event -> (event as? E)?.let(this::on) },
    plugin,
    ignoreCancelled,
)
