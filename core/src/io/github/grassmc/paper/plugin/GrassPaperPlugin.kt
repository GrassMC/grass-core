package io.github.grassmc.paper.plugin

import com.github.shynixn.mccoroutine.folia.SuspendingPlugin
import java.nio.file.Path

/**
 * Represents a Paper plugin with kotlinx-coroutines support.
 *
 * @see [SuspendingPlugin]
 */
interface GrassPaperPlugin : SuspendingPlugin {
    /**
     * The directory that the plugin data's files are located in. The directory may not exist.
     *
     * @see org.bukkit.plugin.Plugin.getDataFolder
     */
    val dataDirectory: Path
}
