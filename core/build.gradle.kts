plugins {
    `maven-publish`
}

dependencies {
    api(kotlin("reflect"))
    api(platform(libs.kotlinx.coroutines.bom))
    api(libs.kotlinx.coroutines.core)
    api(libs.bundles.mccoroutine.folia)
    api(libs.commandapi.bukkit.core)
    api(libs.commandapi.bukkit.kotlin)
}
