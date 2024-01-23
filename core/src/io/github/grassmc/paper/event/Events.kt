@file:Suppress("unused")

package io.github.grassmc.paper.event

import com.github.shynixn.mccoroutine.folia.EventExecutionType
import com.github.shynixn.mccoroutine.folia.callSuspendingEvent
import io.github.grassmc.paper.extensions.Paper
import org.bukkit.event.Event
import org.bukkit.plugin.Plugin

/**
 * Calls an event with the given details.
 * Allows awaiting the completion of suspending event listeners.
 *
 * @param plugin Plugin plugin.
 * @param executionType Allows specifying how suspend receivers are executed.
 * @return Collection of await-able jobs. This job list may be empty if no suspending listener
 * was called. Each job instance represents an await-able job for each method being called in each suspending listener.
 * For awaiting use callSuspendingEvent(...).joinAll().
 *
 * @see [org.bukkit.plugin.PluginManager.callSuspendingEvent]
 */
fun Event.callSuspending(
    plugin: Plugin,
    executionType: EventExecutionType = EventExecutionType.Concurrent,
) = Paper.pluginManager.callSuspendingEvent(this, plugin, executionType)
