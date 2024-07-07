import io.github.grassmc.waddle.paper.extensions.paperApi
import io.github.grassmc.waddle.paper.extensions.paperMc

plugins {
    alias(waddle.plugins.kotlin)
}

repositories {
    paperMc()
}

dependencies {
    compileOnly(paperApi())

    api(platform(waddle.kotlin.bom))
    api(waddle.kotlin.stdlib)
    api(waddle.kotlin.reflect)

    api(platform(waddle.kotlinx.coroutines.bom))
    api(waddle.kotlinx.coroutines.core)

    api(libs.bundles.mccoroutine.folia)
    api(libs.commandapi.bukkit.core)
    api(libs.commandapi.bukkit.kotlin)
}
