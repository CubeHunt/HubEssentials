package net.cubehunt.hubessentials;

import net.cubehunt.hubessentials.config.BaseConfiguration;
import net.cubehunt.hubessentials.config.IConfig;
import org.bukkit.Location;

import java.io.File;

public class Spawn implements IConfig {

    private final HubEssentials plugin;

    private final BaseConfiguration config;

    public Spawn(HubEssentials plugin) {
        this.plugin = plugin;
        this.config = new BaseConfiguration(new File(plugin.getDataFolder(), "spawn.yml"));
        reloadConfig();
    }

    // Retrieves the spawn info from the spawn.yml file
    public Location getSpawn() {
        return config.getLocation("spawn").location();
    }

    // Sets the spawn property in the spawn.yml file
    public void setSpawn(Location location) {
        config.setProperty("spawn", location);
        config.save();
    }

    // Checks if a spawn has been already set in the spawn.yml file
    public boolean spawnExists() {
        return config.hasProperty("spawn");
    }

    // Reloads the plugin
    @Override
    public void reloadConfig() {
        config.load();
    }
}