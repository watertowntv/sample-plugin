import io.papermc.paper.event.server.ServerResourcesReloadedEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import zaqws.zycos.*

class Main : JavaPlugin(), Listener {
    companion object {
        lateinit var plugin: Main
            private set
    }


    override fun onEnable() {
        plugin = this

        if (createConfigFile(plugin)) {
            componentLogger.info(text("New config file generated!"))
        }

        registerCommand()
        register(this)
    }

    override fun onDisable() {
        unregister()
        CoroutineManager.cancel(this)
    }


    private fun registerCommand() {
        registerCommandTree("sample") {
            requiresOp()

            leaf("test") { context ->
                context.sender.sendMessage(text("Hello, world!"))
            }
        }
    }


    @Suppress("unused")
    @EventHandler
    private fun onReload(event: ServerResourcesReloadedEvent) = reloadConfig()
}
