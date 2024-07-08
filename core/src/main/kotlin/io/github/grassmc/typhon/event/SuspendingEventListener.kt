package io.github.grassmc.typhon.event

import io.github.grassmc.typhon.coroutines.mcCoroutine
import io.github.grassmc.typhon.coroutines.newSuspendingEventExecutor
import io.github.grassmc.typhon.coroutines.newSuspendingRegisteredListener
import io.github.grassmc.typhon.plugin.firstJavaPlugin
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.jvm.javaMethod

/**
 * Represents a suspend event listener for a specific event type [E].
 *
 * @param E The event type to listen for.
 */
fun interface SuspendingEventListener<E : Event> : Listener {
    /** Executes a callback when the specified [event] occurs. */
    suspend fun on(event: E)
}

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
    plugin: Plugin = firstJavaPlugin(),
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    noinline contextResolver: (E) -> CoroutineContext,
) {
    val executor =
        newSuspendingEventExecutor(
            E::class.java,
            this::on.javaMethod!!,
            plugin,
            mcCoroutine.getCoroutineSession(plugin),
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
