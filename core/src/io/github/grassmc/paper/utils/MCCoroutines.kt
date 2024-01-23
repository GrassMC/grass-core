package io.github.grassmc.paper.utils

import com.github.shynixn.mccoroutine.folia.CoroutineSession
import com.github.shynixn.mccoroutine.folia.MCCoroutine
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.plugin.EventExecutor
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.RegisteredListener
import java.lang.reflect.Method
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.cast
import kotlin.reflect.full.primaryConstructor

private val mcCoroutine: MCCoroutine by lazy {
    val clazz = Class.forName("com.github.shynixn.mccoroutine.folia.MCCoroutineKt")
    MCCoroutine::class.cast(clazz.getDeclaredMethod("getMcCoroutine").invoke(null))
}
private val SuspendingEventExecutorConstructor by lazy {
    val clazz =
        Class.forName(
            "com.github.shynixn.mccoroutine.folia.service.EventServiceImpl.SuspendingEventExecutor",
        ).kotlin
    requireNotNull(clazz.primaryConstructor) {
        "SuspendingEventExecutor class does not have a primary constructor"
    }
}
private val SuspendingRegisteredListenerConstructor by lazy {
    val clazz =
        Class.forName(
            "com.github.shynixn.mccoroutine.folia.service.EventServiceImpl.SuspendingRegisteredListener",
        ).kotlin
    requireNotNull(clazz.primaryConstructor) {
        "SuspendingRegisteredListener class does not have a primary constructor"
    }
}

/**
 * This class is a wrapper for the MCCoroutine interface, providing convenient access to its hidden functionality.
 */
object MCCoroutineWrapper : MCCoroutine by mcCoroutine

/**
 * Creates a new [EventExecutor] for a suspending event listener.
 */
@PublishedApi
internal fun <E : Event> newSuspendingEventExecutor(
    eventClass: Class<E>,
    method: Method,
    plugin: Plugin,
    coroutineSession: CoroutineSession,
    contextResolver: (E) -> CoroutineContext,
): EventExecutor =
    EventExecutor::class.cast(
        SuspendingEventExecutorConstructor.call(
            eventClass,
            method,
            plugin,
            coroutineSession,
            contextResolver,
        ),
    )

/**
 * Creates a new [RegisteredListener] for a suspending event listener.
 */
@PublishedApi
internal fun newSuspendingRegisteredListener(
    listener: Listener,
    executor: EventExecutor,
    priority: EventPriority,
    plugin: Plugin,
    ignoreCancelled: Boolean,
): RegisteredListener =
    RegisteredListener::class.cast(
        SuspendingRegisteredListenerConstructor.call(
            listener,
            executor,
            priority,
            plugin,
            ignoreCancelled,
        ),
    )
