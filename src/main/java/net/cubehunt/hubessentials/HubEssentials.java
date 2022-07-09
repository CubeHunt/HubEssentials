package net.cubehunt.hubessentials;

import net.cubehunt.hubessentials.commands.spawncommands.SetSpawnCommand;
import net.cubehunt.hubessentials.commands.spawncommands.SpawnCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class HubEssentials extends JavaPlugin {
    // Initializing a logger to print logs into the console
    private static final Logger logger = Logger.getLogger("HubEssentials");

    @Override
    public void onEnable() {
        // Plugin startup logic
        if (logger != super.getLogger()) {
            logger.setParent(super.getLogger());
        }

        new SetSpawnCommand(this);
        new SpawnCommand(this);

        logger.log(Level.INFO, "HubEssentials has been loaded successfully!");
    }

    @Override
    public void onDisable() {
        logger.log(Level.INFO, "HubEssentials has been disabled successfully!");
    }
}
