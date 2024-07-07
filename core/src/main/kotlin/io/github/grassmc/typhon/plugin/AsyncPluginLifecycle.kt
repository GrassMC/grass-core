package io.github.grassmc.typhon.plugin

/**
 * This interface defines the lifecycle of an asynchronous plugin.
 *
 * It provides three methods that need to be implemented by any class that wants to be an asynchronous plugin.
 * These methods are called when the plugin is loaded, enabled, and disabled, respectively.
 */
interface AsyncPluginLifecycle {
    /**
     * Loads the plugin asynchronously.
     *
     * This method is called when the plugin is loaded, before it is enabled.
     */
    suspend fun onLoadAsync()

    /**
     * Enables the plugin asynchronously.
     *
     * This method is called when the plugin is enabled.
     */
    suspend fun onEnableAsync()

    /**
     * Disables the plugin asynchronously.
     *
     * This method is called when the plugin is disabled.
     */
    suspend fun onDisableAsync()
}
