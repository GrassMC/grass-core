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
    api(kotlin("reflect"))
    api(platform(libs.kotlinx.coroutines.bom))
    api(libs.kotlinx.coroutines.core)
    api(libs.bundles.mccoroutine.folia)
    api(libs.commandapi.bukkit.core)
    api(libs.commandapi.bukkit.kotlin)
}
