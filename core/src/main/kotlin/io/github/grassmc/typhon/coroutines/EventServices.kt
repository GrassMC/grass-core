package io.github.grassmc.typhon.coroutines

import com.github.shynixn.mccoroutine.folia.CoroutineSession
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

private fun eventServiceImplSubclass(name: String) =
    Class.forName("com.github.shynixn.mccoroutine.folia.service.EventServiceImpl").kotlin.nestedClasses.first {
        it.simpleName == name
    }

private val SuspendingEventExecutorConstructor by lazy {
    checkNotNull(eventServiceImplSubclass("SuspendingEventExecutor").primaryConstructor)
}
private val SuspendingRegisteredListenerConstructor by lazy {
    checkNotNull(eventServiceImplSubclass("SuspendingRegisteredListener").primaryConstructor)
}

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
) = EventExecutor::class.cast(
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
) = RegisteredListener::class.cast(
    SuspendingRegisteredListenerConstructor.call(
        listener,
        executor,
        priority,
        plugin,
        ignoreCancelled,
    ),
)
