plugins {
    alias(waddle.plugins.kotlin)
    alias(waddle.plugins.paper)
    alias(waddle.plugins.shadow)
}

dependencies {
    api(project(":typhon-core"))
    api(libs.commandapi.bukkit.shade)
}
