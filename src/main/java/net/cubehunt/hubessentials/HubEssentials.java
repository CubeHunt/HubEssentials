package net.cubehunt.hubessentials;

import net.cubehunt.hubessentials.commandblocker.CommandBlocker;
import net.cubehunt.hubessentials.commandblocker.CommandToBlock;
import net.cubehunt.hubessentials.commands.HubEssentialsCommand;
import net.cubehunt.hubessentials.commands.spawncommands.SetSpawnCommand;
import net.cubehunt.hubessentials.commands.spawncommands.SpawnCommand;
import net.cubehunt.hubessentials.config.IConfig;
import net.cubehunt.hubessentials.listeners.CommandBlockerListener;
import net.cubehunt.hubessentials.listeners.SpawnListeners;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class HubEssentials extends JavaPlugin {
    // Initializing a logger to print logs into the console
    private static final Logger logger = Logger.getLogger("HubEssentials");

    @Override
    public void onEnable() {
        if (logger != super.getLogger()) {
            logger.setParent(super.getLogger());
        }

        registerConfigs();
        registerCommands();
        reload();

        logger.log(Level.INFO, "HubEssentials has been loaded successfully!");
    }


    // Plugin Shutdown Logic
    @Override
    public void onDisable() {
        logger.log(Level.INFO, "HubEssentials has been disabled successfully!");
    }
}
