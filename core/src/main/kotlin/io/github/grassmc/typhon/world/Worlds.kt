package io.github.grassmc.typhon.world

import io.github.grassmc.typhon.key.asBukkit
import io.github.grassmc.typhon.server.PaperServer
import net.kyori.adventure.key.Key
import org.bukkit.World
import java.util.UUID

/**
 * Retrieves the list of all worlds in the server.
 *
 * @see org.bukkit.Server.getWorlds
 */
val worlds: List<World> get() = PaperServer.worlds

/**
 * Finds a world by its [name].
 *
 * @see org.bukkit.Server.getWorld
 */
fun worldOrNull(name: String) = PaperServer.getWorld(name)

/**
 * Finds a world by its [id].
 *
 * @see org.bukkit.Server.getWorld
 */
fun worldOrNull(id: UUID) = PaperServer.getWorld(id)

/**
 * Finds a world by its [key].
 *
 * @see org.bukkit.Server.getWorld
 */
fun worldOrNull(key: Key) = PaperServer.getWorld(key.asBukkit())

/**
 * Retrieves a world by its [name].
 *
 * @throws IllegalArgumentException If no world with the specified name is found.
 * @see org.bukkit.Server.getWorld
 */
fun world(name: String) = requireNotNull(worldOrNull(name)) { "World with name $name not found" }

/**
 * Retrieves a world by its [id].
 *
 * @throws IllegalArgumentException If no world with the specified unique id is found.
 * @see org.bukkit.Server.getWorld
 */
fun world(id: UUID) = requireNotNull(worldOrNull(id)) { "World with uuid $id not found" }

/**
 * Retrieves a world by its [key].
 *
 * @throws IllegalArgumentException If no world with the specified key is found.
 * @see org.bukkit.Server.getWorld
 */
fun world(key: Key) = requireNotNull(worldOrNull(key)) { "World with key $key not found" }
