package net.cubehunt.hubessentials;

import lombok.Getter;
import net.cubehunt.hubessentials.commandblocker.CommandBlocker;
import net.cubehunt.hubessentials.commandblocker.CommandToBlock;
import net.cubehunt.hubessentials.commands.HubEssentialsCommand;
import net.cubehunt.hubessentials.commands.NicknameCommand;
import net.cubehunt.hubessentials.commands.spawncommands.SetSpawnCommand;
import net.cubehunt.hubessentials.commands.spawncommands.SpawnCommand;
import net.cubehunt.hubessentials.config.IConfig;
import net.cubehunt.hubessentials.database.DatabaseData;
import net.cubehunt.hubessentials.database.UserData;
import net.cubehunt.hubessentials.hideplayer.HidePlayer;
import net.cubehunt.hubessentials.listeners.*;
import net.cubehunt.hubessentials.perms.PermissionsHandler;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class HubEssentials extends JavaPlugin {
    // Initializing a logger to print logs into the console
    private static final Logger logger = Logger.getLogger("HubEssentials");

    private static BukkitAudiences adventure;

    private final List<IConfig> configList = new ArrayList<>();

    private UserMap userMap;

    @Getter
    private DatabaseData databaseData;

    @Getter
    private UserData userData;

    // PermissionHandler Functionality
    @Getter
    private PermissionsHandler permissionsHandler;

    // Spawn Functionality
    private Spawn spawn;

    // CommandBlocker Functionality
    private CommandBlocker commandBlocker;

    // HidePlayer Functionality

    @Getter
    private HidePlayer hidePlayer;


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
        new NicknameCommand(this);
    }

    private void registerListeners(final PluginManager pm) {
        final PluginListener pluginListener = new PluginListener(this);
        pm.registerEvents(pluginListener, this);

        final PlayerListener playerListener = new PlayerListener(this);
        pm.registerEvents(playerListener, this);

        final ChatListener chatListener = new ChatListener(this);
        pm.registerEvents(chatListener, this);

        final HidePlayerListener hidePlayerListener = new HidePlayerListener(this);
        pm.registerEvents(hidePlayerListener, this);

        final SpawnListener spawnListener = new SpawnListener(this);
        pm.registerEvents(spawnListener, this);
    }

    private void registerFunctions() {
        userMap = new UserMap(this);

        databaseData = new DatabaseData(this);
        configList.add(databaseData);

        userData = new UserData(this);

        permissionsHandler = new PermissionsHandler(this, true);

        spawn = new Spawn(this);
        configList.add(spawn);

        commandBlocker = new CommandBlocker(this);
        configList.add(commandBlocker);

        hidePlayer = new HidePlayer(this);
        configList.add(hidePlayer);
    }

    // UserMap - Methods
    public boolean userExists(final UUID uuid) {
        return userMap.exists(uuid);
    }

    public void loadUser(final Player player) {
        userMap.loadUser(player);
    }

    public void unloadUser(final UUID uuid) {
        userMap.unloadUser(uuid);
    }

    public User getUser(final UUID uuid) {
        return userMap.getUser(uuid);
    }

    public User getUser(final Player player) {
        return userMap.getUser(player);
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

    /* DatabaseData Methods */
    public Connection getConnection() {
        try {
            return databaseData.getDbSource().getConnection();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database connection error! Try to restart your server");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        return null;
    }
}
