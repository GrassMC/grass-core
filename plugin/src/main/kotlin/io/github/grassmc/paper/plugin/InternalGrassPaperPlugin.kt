package io.github.grassmc.paper.plugin

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import dev.jorel.commandapi.CommandAPILogger

@Suppress("unused")
class InternalGrassPaperPlugin : GrassPaperJavaPlugin() {
    override suspend fun onDisableAsync() {
        CommandAPI.onDisable()
    }

    override suspend fun onLoadAsync() {
        val commandApiBukkitConfig = CommandAPIBukkitConfig(this).shouldHookPaperReload(true)
        CommandAPI.setLogger(CommandAPILogger.fromSlf4jLogger(slF4JLogger))
        CommandAPI.onLoad(commandApiBukkitConfig)
    }

    override suspend fun onEnableAsync() {
        CommandAPI.onEnable()
    }
}
