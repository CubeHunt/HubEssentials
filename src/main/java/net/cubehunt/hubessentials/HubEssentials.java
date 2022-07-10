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

    private final List<IConfig> configList = new ArrayList<>();

    // Spawn Functionality
    private Spawn spawn;

    // CommandBlocker Functionality
    private CommandBlocker commandBlocker;


    // Plugin Startup Logic
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

    public void reload() {
        for (final IConfig config : configList) {
            config.reloadConfig();
        }

        registerListeners(getServer().getPluginManager());
    }

    private void registerCommands() {
        new HubEssentialsCommand(this);
        new SetSpawnCommand(this);
        new SpawnCommand(this);
    }

    private void registerListeners(final PluginManager pm) {
        HandlerList.unregisterAll(this);

        final SpawnListeners spawnListeners = new SpawnListeners(this);
        pm.registerEvents(spawnListeners, this);

        final CommandBlockerListener commandBlockerListener = new CommandBlockerListener(this);
        pm.registerEvents(commandBlockerListener, this);
    }

    private void registerConfigs() {
        spawn = new Spawn(this);
        configList.add(spawn);

        commandBlocker = new CommandBlocker(this);
        configList.add(commandBlocker);
    }


    /* Spawn Functionality - Methods */
    public Location getSpawn() {
        return spawn.getSpawn();
    }
    public void setSpawn(Location location) {
        spawn.setSpawn(location);
    }
    public boolean spawnExists() {
        return spawn.spawnExists();
    }

    /* CommandBlocker Functionality - Methods */
    public Set<CommandToBlock> getBlockedCommands() {
        return commandBlocker.getBlockedCommands();
    }
}
