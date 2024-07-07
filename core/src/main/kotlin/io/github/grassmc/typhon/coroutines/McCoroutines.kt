package io.github.grassmc.typhon.coroutines

import com.github.shynixn.mccoroutine.folia.MCCoroutine

@PublishedApi
internal val mcCoroutine: MCCoroutine by lazy {
    Class
        .forName("com.github.shynixn.mccoroutine.folia.MCCoroutineKt")
        .getDeclaredMethod("getMcCoroutine")
        .invoke(null) as MCCoroutine
}
