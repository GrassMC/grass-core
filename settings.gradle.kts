import io.github.tozydev.fastmodule.dsl.fastModule
import io.github.tozydev.fastmodule.dsl.module
import io.github.tozydev.fastmodule.modules.ModuleType

plugins {
    id("io.github.tozydev.fast-module") version "0.4.0"
}

rootProject.name = "grass-paper"

fastModule {
    module(ModuleType.PAPER_LIB, "core")
    module(ModuleType.PAPER_PLUGIN, "plugin")
}
