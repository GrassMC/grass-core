package io.github.grassmc.typhon.audiences

import io.github.grassmc.typhon.text.orEmpty
import io.github.grassmc.typhon.text.richText
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.sound.Sound.Source
import net.kyori.adventure.sound.Sound.Type
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.title.Title
import kotlin.time.Duration
import kotlin.time.toJavaDuration
import kotlin.time.toKotlinDuration

/**
 * Represents a message that can be sent to a [Audience].
 *
 * @see TextMessage
 * @see ActionBarMessage
 * @see TitleMessage
 * @see SoundMessage
 * @see CompositeMessage
 */
sealed interface Message

/**
 * Represents a text message that can be sent to an audience.
 *
 * @property text The text content of the message.
 *
 * @see Audience.sendMessage
 */
data class TextMessage(
    val text: String,
) : Message

/**
 * Represents an action bar message that can be sent to an audience.
 *
 * @property actionBar The action bar content of the message.
 *
 * @see Audience.sendActionBar
 */
data class ActionBarMessage(
    val actionBar: String,
) : Message

/**
 * Represents a title message that can be displayed on audience screen.
 *
 * @property title The title of the message.
 * @property subtitle The subtitle of the message.
 * @property times The times for the message to fadeIn, stay, and fadeOut.
 *
 * @see Audience.showTitle
 */
data class TitleMessage(
    val title: String? = null,
    val subtitle: String? = null,
    val times: Times? = null,
) : Message {
    /**
     * Represents the duration for the fadeIn, stay, and fadeOut effects of a title message.
     *
     * @property fadeIn The duration of the fadeIn effect in milliseconds.
     * @property stay The duration the message should stay on screen in milliseconds.
     * @property fadeOut The duration of the fadeOut effect in milliseconds.
     */
    data class Times(
        val fadeIn: Duration = DEFAULT_FADE_IN,
        val stay: Duration = DEFAULT_STAY,
        val fadeOut: Duration = DEFAULT_FADE_OUT,
    )

    companion object {
        private val DEFAULT_FADE_IN = Title.DEFAULT_TIMES.fadeIn().toKotlinDuration()
        private val DEFAULT_STAY = Title.DEFAULT_TIMES.stay().toKotlinDuration()
        private val DEFAULT_FADE_OUT = Title.DEFAULT_TIMES.fadeOut().toKotlinDuration()
    }
}

/**
 * Represents a sound that can be played to an audience.
 *
 * @property type The sound type to play.
 * @property source The source of the sound.
 * @property volume The volume of the sound. Must be a positive number.
 * @property pitch The pitch of the sound. Must be between -1 and 1.
 *
 * @see org.bukkit.Sound
 * @see Audience.playSound
 */
data class SoundMessage(
    val type: Type,
    val source: Source? = null,
    val volume: Float? = null,
    val pitch: Float? = null,
) : Message

/**
 * Represents a composite message that can be sent to an audience.
 *
 * @property messages The list of messages in the composite message.
 */
data class CompositeMessage(
    val messages: List<Message> = emptyList(),
) : Message

/**
 * Sends a message to the audience.
 *
 * This function use [net.kyori.adventure.text.minimessage.MiniMessage] to deserialize the message string to a
 * component with optional [resolver]
 */
fun Audience.sendMessage(
    message: Message,
    resolver: TagResolver? = null,
) {
    when (message) {
        is TextMessage -> sendMessage(message.text.richText(resolver))
        is ActionBarMessage -> sendActionBar(message.actionBar.richText(resolver))
        is TitleMessage -> showTitle(message.toAdventureTitle(resolver))
        is SoundMessage -> playSound(message.toAdventureSound())
        is CompositeMessage -> message.messages.forEach { sendMessage(it, resolver) }
    }
}

/**
 * Sends a message to the specified audience.
 *
 * @see sendMessage
 */
fun Message.send(
    audience: Audience,
    resolver: TagResolver? = null,
) = audience.sendMessage(this, resolver)

private fun TitleMessage.toAdventureTitle(resolver: TagResolver?) =
    Title.title(
        title?.richText(resolver).orEmpty(),
        subtitle?.richText(resolver).orEmpty(),
        times?.toAdventureTimes(),
    )

private fun TitleMessage.Times.toAdventureTimes() =
    Title.Times.times(
        fadeIn.toJavaDuration(),
        stay.toJavaDuration(),
        fadeOut.toJavaDuration(),
    )

private fun SoundMessage.toAdventureSound() =
    Sound.sound { builder ->
        builder.type(type)
        source?.let { builder.source(it) }
        volume?.let { builder.volume(it) }
        pitch?.let { builder.pitch(it) }
    }
