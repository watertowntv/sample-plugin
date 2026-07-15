import io.papermc.paper.event.server.ServerResourcesReloadedEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class EventManager : Listener {
    @Suppress("unused")
    @EventHandler
    private fun onReload(event: ServerResourcesReloadedEvent) {
        Main.plugin.reloadConfig()
    }
}