@file:Suppress("removal", "DEPRECATION")

package io.github.grassmc.paper.event

import io.github.grassmc.paper.server.Paper
import io.github.grassmc.paper.utils.MCCoroutineWrapper
import io.github.grassmc.paper.utils.newSuspendingEventExecutor
import io.github.grassmc.paper.utils.newSuspendingRegisteredListener
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.SimplePluginManager
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.javaMethod

/**
 * Represents a suspend event listener for a specific event type [E].
 *
 * @param E The event type to listen for.
 */
fun interface SuspendingEventListener<E : Event> : Listener {
    suspend fun on(event: E)
}

private val getEventListenerFunction by lazy {
    requireNotNull(SimplePluginManager::class.declaredFunctions.find { it.name == "getEventListeners" }) {
        "SimplePluginManager::getEventListeners not found"
    }
}

@PublishedApi
internal fun getEventListeners(eventClass: Class<out Event>) =
    getEventListenerFunction.call(Paper.pluginManager, eventClass) as HandlerList

/**
 * Registers a suspend event listener of type [E].
 *
 * @param E The event type to listen for.
 * @param plugin The plugin to register this listener for.
 * @param priority The priority of the event.
 * @param ignoreCancelled Whether to ignore canceled events.
 * @param contextResolver A function to resolve the coroutine context for the event.
 */
inline fun <reified E : Event> SuspendingEventListener<E>.register(
    plugin: Plugin,
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    noinline contextResolver: (E) -> CoroutineContext,
) {
    val executor =
        newSuspendingEventExecutor(
            E::class.java,
            this::on.javaMethod!!,
            plugin,
            MCCoroutineWrapper.getCoroutineSession(plugin),
            contextResolver,
        )
    val registeredListener =
        newSuspendingRegisteredListener(
            this,
            executor,
            priority,
            plugin,
            ignoreCancelled,
        )
    getEventListeners(E::class.java).register(registeredListener)
}
