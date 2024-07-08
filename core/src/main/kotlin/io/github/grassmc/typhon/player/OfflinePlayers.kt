package io.github.grassmc.typhon.player

import io.github.grassmc.typhon.server.PaperServer
import java.util.UUID

/**
 * Gets the player by the given [name], regardless if they are offline or online.
 *
 * This method may involve a blocking web request to get the UUID for the given name.
 * This will always return a player object, even if the player has never joined the server.
 *
 * @see org.bukkit.Server.getOfflinePlayer
 */
fun offlinePlayer(name: String) = PaperServer.getOfflinePlayer(name)

/**
 * Gets the player by the given [id], regardless if they are offline or online.
 *
 * This will always return a player object, even if the player has never joined the server.
 *
 * @see org.bukkit.Server.getOfflinePlayer
 */
fun offlinePlayer(id: UUID) = PaperServer.getOfflinePlayer(id)
