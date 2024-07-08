@file:Suppress("removal")

package io.github.grassmc.typhon.event

import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.plugin.IllegalPluginAccessException
import java.lang.reflect.Modifier

// Copy from org.bukkit.plugin.PluginManager
@PublishedApi
internal fun getEventListeners(type: Class<out Event>): HandlerList {
    try {
        val method = getRegistrationClass(type).getDeclaredMethod("getHandlerList")
        method.isAccessible = true

        if (!Modifier.isStatic(method.modifiers)) {
            throw IllegalAccessException("getHandlerList must be static")
        }

        return method.invoke(null) as HandlerList
    } catch (e: Exception) {
        throw IllegalPluginAccessException("Error while registering listener for event type $type: $e")
    }
}

private fun getRegistrationClass(clazz: Class<out Event>): Class<out Event> {
    try {
        clazz.getDeclaredMethod("getHandlerList")
        return clazz
    } catch (e: NoSuchMethodException) {
        if (clazz.superclass != null &&
            clazz.superclass != Event::class.java &&
            Event::class.java.isAssignableFrom(clazz.superclass)
        ) {
            return getRegistrationClass(clazz.superclass.asSubclass(Event::class.java))
        }
        throw IllegalPluginAccessException(
            "Unable to find handler list for event " + clazz.name + ". Static getHandlerList method required!",
        )
    }
}
