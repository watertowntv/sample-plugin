import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import zaqws.zycos.CoroutineManager
import zaqws.zycos.createConfigFile
import zaqws.zycos.execute
import zaqws.zycos.leaf
import zaqws.zycos.registerCommandTree
import zaqws.zycos.requiresOp
import zaqws.zycos.text

class Main : JavaPlugin() {
    companion object {
        lateinit var plugin: Main
            private set
    }


    override fun onEnable() {
        plugin = this

        if(createConfigFile(plugin)) {
            componentLogger.info(text("New config file generated!"))
        }

        server.pluginManager.registerEvents(EventManager(), plugin)

        registerCommand()
    }

    override fun onDisable() {
        CoroutineManager.cancel(this)
    }


    private fun registerCommand() {
        registerCommandTree("sample") {
            requiresOp()
            execute { it.source.sender.sendCommandMessage() }

            leaf("test") { context ->
                context.source.sender.sendMessage(text("Hello, world!"))
            }
        }
    }


    private fun CommandSender.sendCommandMessage() {
        val amount = 32
        val name = pluginMeta.name
        val offset = "-".repeat((amount - name.length) / 2)

        sendMessage(text("$offset$name$offset", NamedTextColor.GOLD))
        // Edit ==============================================================
        sendMessage(text("/sample test", NamedTextColor.GOLD))
        // Edit ==============================================================
        sendMessage(text(""))
        sendMessage(text("By ${pluginMeta.authors.joinToString(", ")}", NamedTextColor.GOLD))
        sendMessage(text("-".repeat(offset.length * 2 + name.length - 1), NamedTextColor.GOLD))
    }
}
