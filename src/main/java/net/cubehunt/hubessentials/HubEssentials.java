package net.cubehunt.hubessentials;

import lombok.Getter;
import net.cubehunt.hubessentials.commandblocker.CommandBlocker;
import net.cubehunt.hubessentials.commandblocker.CommandToBlock;
import net.cubehunt.hubessentials.commands.HubEssentialsCommand;
import net.cubehunt.hubessentials.commands.spawncommands.SetSpawnCommand;
import net.cubehunt.hubessentials.commands.spawncommands.SpawnCommand;
import net.cubehunt.hubessentials.config.IConfig;
import net.cubehunt.hubessentials.database.DatabaseData;
import net.cubehunt.hubessentials.listeners.ChatListener;
import net.cubehunt.hubessentials.listeners.CommandBlockerListener;
import net.cubehunt.hubessentials.listeners.PluginListener;
import net.cubehunt.hubessentials.listeners.SpawnListeners;
import net.cubehunt.hubessentials.perms.PermissionsHandler;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class HubEssentials extends JavaPlugin {
    // Initializing a logger to print logs into the console
    private static final Logger logger = Logger.getLogger("HubEssentials");

    private static BukkitAudiences adventure;

    private final List<IConfig> configList = new ArrayList<>();

    // PermissionHandler Functionality
    @Getter
    private PermissionsHandler permissionsHandler;

    private DatabaseData databaseData;

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

        adventure = BukkitAudiences.create(this);

        registerFunctions();
        registerCommands();
        registerListeners(getServer().getPluginManager());

        logger.log(Level.INFO, "HubEssentials has been loaded successfully!");
    }


    // Plugin Shutdown Logic
    @Override
    public void onDisable() {
        if(adventure != null) {
            adventure.close();
            adventure = null;
        }
        databaseData.close();
        logger.log(Level.INFO, "HubEssentials has been disabled successfully!");
    }

    public void reload() {
        for (final IConfig config : configList) {
            config.reloadConfig();
        }
    }

    private void registerCommands() {
        new HubEssentialsCommand(this);
        new SetSpawnCommand(this);
        new SpawnCommand(this);
    }

    private void registerListeners(final PluginManager pm) {
        final PluginListener pluginListener = new PluginListener(this);
        pm.registerEvents(pluginListener, this);

        final SpawnListeners spawnListeners = new SpawnListeners(this);
        pm.registerEvents(spawnListeners, this);

        final CommandBlockerListener commandBlockerListener = new CommandBlockerListener(this);
        pm.registerEvents(commandBlockerListener, this);

        final ChatListener chatListener = new ChatListener(this);
        pm.registerEvents(chatListener, this);
    }

    private void registerFunctions() {
        permissionsHandler = new PermissionsHandler(this, true);

        databaseData = new DatabaseData(this);
        configList.add(databaseData);

        spawn = new Spawn(this);
        configList.add(spawn);

        commandBlocker = new CommandBlocker(this);
        configList.add(commandBlocker);
    }

    // BukkitAudiences - Methods
    public static @NonNull BukkitAudiences adventure() {
        if(adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return adventure;
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
