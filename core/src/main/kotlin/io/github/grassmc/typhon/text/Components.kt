package io.github.grassmc.typhon.text

import io.github.grassmc.typhon.text.minimessage.miniMessage
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

/**
 * Deserializes the given string using _MiniMessage_ format with optional [resolver].
 *
 * @see net.kyori.adventure.text.minimessage.MiniMessage.deserialize
 */
fun String.richText(resolver: TagResolver? = null) =
    if (resolver == null) miniMessage.deserialize(this) else miniMessage.deserialize(this, resolver)

/** Returns this Component if it is not null, otherwise returns an empty Component. */
fun Component?.orEmpty() = this ?: Component.empty()
