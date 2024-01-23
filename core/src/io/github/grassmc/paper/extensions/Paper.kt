package io.github.grassmc.paper.extensions

import org.bukkit.Bukkit
import org.bukkit.Server

/**
 * Represents the [Server] singleton, which is a wrapper for [Bukkit.getServer].
 *
 * @see Bukkit.getServer
 */
object Paper : Server by Bukkit.getServer()
