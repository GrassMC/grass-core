package io.github.grassmc.typhon.plugin

import io.github.grassmc.typhon.server.PaperServer
import io.papermc.paper.plugin.provider.classloader.ConfiguredPluginClassLoader
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

/**
 * Retrieves the instance of a specified plugin class.
 *
 * @see JavaPlugin.getPlugin
 */
inline fun <reified T : JavaPlugin> javaPlugin() = JavaPlugin.getPlugin(T::class.java)

/** Retrieves the plugin with the specified [name], if it is of the specified type [T]. */
inline fun <reified T : Plugin> pluginOrNull(name: String) = PaperServer.pluginManager.getPlugin(name) as? T

/**
 * Retrieves the plugin with the specified [name], if it is of the specified type.
 *
 * @throws IllegalArgumentException if the plugin with the given name is not found.
 * @throws ClassCastException if the plugin is not of the specified type.
 */
inline fun <reified T : Plugin> plugin(name: String) =
    requireNotNull(pluginOrNull(name)) { "Plugin $name not found" } as T

private val stackWalkerThreadLocal =
    ThreadLocal.withInitial { StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE) }

private val stackWalker get() = stackWalkerThreadLocal.get()

/**
 * Retrieves the currently working plugin that calls this method.
 *
 * This method use [StackWalker] to retrieve the first caller class that it's class loader is a
 * [ConfiguredPluginClassLoader].
 *
 * @throws IllegalArgumentException If it cannot find JavaPlugin
 */
@Suppress("UnstableApiUsage")
@PublishedApi
internal fun firstJavaPlugin() =
    stackWalker
        .walk { stream ->
            stream
                .map { it.declaringClass.classLoader as? ConfiguredPluginClassLoader }
                .filter { it != null }
                .map { it?.plugin }
                .findFirst()
        }.orElseThrow { IllegalArgumentException("Cannot find JavaPlugin") }!!
