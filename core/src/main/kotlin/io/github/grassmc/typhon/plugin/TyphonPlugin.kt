package io.github.grassmc.typhon.plugin

import org.bukkit.plugin.Plugin
import java.nio.file.Path

/**
 * Represents a Paper plugin with Kotlin Coroutines support with suspendable lifecycle functions.
 */
interface TyphonPlugin :
    Plugin,
    AsyncPluginLifecycle {
    /**
     * The directory that the plugin data's files are located in. The directory may not exist.
     *
     * @see getDataFolder
     */
    val dataDirectory: Path
}
