import io.github.tozydev.fastmodule.dsl.fastModule
import io.github.tozydev.fastmodule.dsl.module
import io.github.tozydev.fastmodule.modules.ModuleType

plugins {
    id("io.github.tozydev.fast-module") version "0.5.1"
}

rootProject.name = "typhon"

fastModule {
    module(ModuleType.PAPER_LIB, "core") {
        moduleName = "${rootProject.name}-core"
    }
    module(ModuleType.PAPER_PLUGIN, "plugin") {
        moduleName = "${rootProject.name}-plugin"
    }
}
