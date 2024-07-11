import io.github.grassmc.waddle.settings.subproject

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.github.com/GrassMC/waddle") {
            name = "githubPackages"
            credentials {
                username = providers.gradleProperty("github.user").orNull
                password = providers.gradleProperty("github.token").orNull
            }
        }
    }
}

plugins {
    id("io.github.grassmc.waddle") version "3.0.0"
}

rootProject.name = "typhon"

subproject(file("core"))
subproject(file("plugin"))
