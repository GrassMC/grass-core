package io.github.grassmc.typhon.plugin

import io.github.grassmc.typhon.coroutines.mcCoroutine
import kotlinx.coroutines.runBlocking
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Path

/**
 * Base class for Typhon Kotlin plugins.
 * This class provides the functionality for a Paper plugin with Kotlin Coroutines support.
 */
abstract class TyphonKotlinPlugin :
    JavaPlugin(),
    TyphonPlugin {
    override val dataDirectory: Path = dataFolder.toPath()

    override suspend fun onLoadAsync() = Unit

    override suspend fun onEnableAsync() = Unit

    override suspend fun onDisableAsync() = Unit

    final override fun onLoad() {
        runBlocking {
            onLoadAsync()
        }
    }

    final override fun onEnable() {
        mcCoroutine.getCoroutineSession(this).isManipulatedServerHeartBeatEnabled = true
        runBlocking {
            onEnableAsync()
        }
        server.servicesManager
        mcCoroutine.getCoroutineSession(this).isManipulatedServerHeartBeatEnabled = false
    }

    final override fun onDisable() {
        runBlocking {
            onDisableAsync()
        }
    }
}
