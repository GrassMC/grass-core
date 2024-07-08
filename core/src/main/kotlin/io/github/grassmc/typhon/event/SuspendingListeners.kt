package io.github.grassmc.typhon.event

import com.github.shynixn.mccoroutine.folia.registerSuspendingEvents
import io.github.grassmc.typhon.plugin.firstJavaPlugin
import io.github.grassmc.typhon.server.PaperServer
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KClass

/**
 * Registers this [Listener] to receive suspending events from the specified [plugin].
 *
 * @param plugin the plugin to register the listener with (defaults to the calling plugin)
 * @param eventDispatchers A mapping for each used event type in the given listener because Folia uses different
 *                         schedulers for different event types.
 */
@Suppress("unused")
fun Listener.registerAllSuspending(
    plugin: Plugin = firstJavaPlugin(),
    eventDispatchers: Map<KClass<out Event>, (event: Event) -> CoroutineContext>,
) = PaperServer.pluginManager.registerSuspendingEvents(this, plugin, eventDispatchers.mapKeys { it.key.java })

/**
 * Registers a suspending event listener [action] for a specific event type [E] in the context of a plugin.
 *
 * @param E The event type to listen for.
 * @param plugin The plugin to register this listener for.
 * @param contextResolver A function that can resolve the coroutine context for the event.
 * @param priority The priority of the event listener (default: `NORMAL`).
 * @param ignoreCancelled Determines whether canceled events should be ignored (default: `false`).
 * @param action The suspending action to be executed when the event occurs.
 * @return The suspending event listener instance.
 */
inline fun <reified E : Event> listenSuspending(
    plugin: Plugin = firstJavaPlugin(),
    noinline contextResolver: (E) -> CoroutineContext,
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    crossinline action: suspend (E) -> Unit,
) = SuspendingEventListener<E> { event -> action(event) }.also {
    it.register(plugin, priority, ignoreCancelled, contextResolver)
}
