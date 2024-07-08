package io.github.grassmc.typhon.text.minimessage

import net.kyori.adventure.text.minimessage.MiniMessage

/**
 * Returns the simple instance of [MiniMessage] with default settings.
 *
 * @see MiniMessage.miniMessage
 */
val miniMessage by lazy { MiniMessage.miniMessage() }
