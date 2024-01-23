@file:Suppress("unused")

package io.github.grassmc.paper.server

import org.bukkit.entity.Player
import java.util.UUID

/**
 * Retrieves the online players in the server.
 *
 * @see org.bukkit.Server.getOnlinePlayers
 */
val onlinePlayers: Collection<Player> get() = Paper.onlinePlayers

/**
 * Finds a player by [name].
 *
 * This method may not return objects for offline players.
 * If [ignoreCase] is true, the search is case-insensitive.
 *
 * @see org.bukkit.Server.getPlayer
 * @see org.bukkit.Server.getPlayerExact
 */
fun findPlayer(
    name: String,
    ignoreCase: Boolean = false,
) = if (ignoreCase) Paper.getPlayer(name) else Paper.getPlayerExact(name)

/**
 * Finds a player by their [uniqueId].
 *
 * This method may not return objects for offline players.
 * For offline players, use [offlinePlayer] instead.
 *
 * @see org.bukkit.Server.getPlayer
 */
fun findPlayer(uniqueId: UUID) = Paper.getPlayer(uniqueId)

/**
 * Retrieves a player by [name].
 * If [ignoreCase] is true, the search is case-insensitive.
 *
 * @throws IllegalArgumentException If no online player with the specified name is found.
 */
fun player(
    name: String,
    ignoreCase: Boolean = false,
) = requireNotNull(findPlayer(name, ignoreCase)) { "Player with name $name is not online" }

/**
 * Retrieves a player by their [uniqueId].
 *
 * @throws IllegalArgumentException If no online player with the specified unique id is found.
 */
fun player(uniqueId: UUID) = requireNotNull(findPlayer(uniqueId)) { "Player with uuid $uniqueId is not online" }

/**
 * Gets the player by the given [name], regardless if they are offline or online.
 *
 * This method may involve a blocking web request to get the UUID for the given name.
 * This will always return a player object, even if the player has never joined the server
 *
 * @see org.bukkit.Server.getOfflinePlayer
 */
fun offlinePlayer(name: String) = Paper.getOfflinePlayer(name)

/**
 * Gets the player by the given [uniqueId], regardless if they are offline or online.
 *
 * This will always return a player object, even if the player has never joined the server
 *
 * @see org.bukkit.Server.getOfflinePlayer
 */
fun offlinePlayer(uniqueId: UUID) = Paper.getOfflinePlayer(uniqueId)
