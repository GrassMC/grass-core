import io.github.grassmc.waddle.settings.subproject

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.github.com/GrassMC/waddle") {
            name = "githubPackages"
            credentials(PasswordCredentials::class)
        }
    }
}

plugins {
    id("io.github.grassmc.waddle") version "2.2.0"
}

rootProject.name = "typhon"

subproject(file("core"))
subproject(file("plugin"))
