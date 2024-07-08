package io.github.grassmc.typhon.player

import io.github.grassmc.typhon.server.PaperServer
import org.bukkit.entity.Player
import java.util.UUID

/**
 * Retrieves the online players in the server.
 *
 * @see org.bukkit.Server.getOnlinePlayers
 */
val onlinePlayers: Collection<Player> get() = PaperServer.onlinePlayers

/**
 * Finds the associated with the given [name].
 *
 * This method may not return objects for offline players.
 * If [ignoreCase] is true, the search is case-insensitive.
 *
 * @see org.bukkit.Server.getPlayerExact
 */
fun playerOrNull(
    name: String,
    ignoreCase: Boolean = false,
) = if (ignoreCase) PaperServer.getPlayer(name) else PaperServer.getPlayerExact(name)

/**
 * Finds a player by their [id].
 *
 * This method may not return objects for offline players.
 * For offline players, use [offlinePlayer] instead.
 *
 * @see org.bukkit.Server.getPlayer
 */
fun playerOrNull(id: UUID) = PaperServer.getPlayer(id)

/**
 * Retrieves a player by [name].
 *
 * If [ignoreCase] is true, the search is case-insensitive.
 *
 * @throws IllegalArgumentException If no online player with the specified name is found.
 */
fun player(
    name: String,
    ignoreCase: Boolean = false,
) = requireNotNull(playerOrNull(name, ignoreCase)) { "Player with name $name is not online" }

/**
 * Retrieves a player by their [id].
 *
 * @throws IllegalArgumentException If no online player with the specified unique id is found.
 */
fun player(id: UUID) = requireNotNull(playerOrNull(id)) { "Player with uuid $id is not online" }
