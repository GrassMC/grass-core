@file:Suppress("unused")

package io.github.grassmc.paper.server

import org.bukkit.NamespacedKey
import org.bukkit.World
import java.util.UUID

/**
 * Retrieves the list of all worlds in the server.
 *
 * @see org.bukkit.Server.getWorlds
 */
val worlds: List<World> get() = Paper.worlds

/**
 * Finds a world by its [name].
 *
 * @see org.bukkit.Server.getWorld
 */
fun findWorld(name: String) = Paper.getWorld(name)

/**
 * Finds a world by its [uuid].
 *
 * @see org.bukkit.Server.getWorld
 */
fun findWorld(uuid: UUID) = Paper.getWorld(uuid)

/**
 * Finds a world by its [key].
 *
 * @see org.bukkit.Server.getWorld
 */
fun findWorld(key: NamespacedKey) = Paper.getWorld(key)

/**
 * Retrieves a world by its [name].
 *
 * @throws IllegalArgumentException If no world with the specified name is found.
 * @see org.bukkit.Server.getWorld
 */
fun world(name: String) = requireNotNull(findWorld(name)) { "World with name $name not found" }

/**
 * Retrieves a world by its [uuid].
 *
 * @throws IllegalArgumentException If no world with the specified unique id is found.
 * @see org.bukkit.Server.getWorld
 */
fun world(uuid: UUID) = requireNotNull(findWorld(uuid)) { "World with uuid $uuid not found" }

/**
 * Retrieves a world by its [key].
 *
 * @throws IllegalArgumentException If no world with the specified key is found.
 * @see org.bukkit.Server.getWorld
 */
fun world(key: NamespacedKey) = requireNotNull(findWorld(key)) { "World with key $key not found" }
