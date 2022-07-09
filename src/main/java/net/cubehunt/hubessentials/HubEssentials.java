package net.cubehunt.hubessentials;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class HubEssentials extends JavaPlugin {

    private static final Logger LOGGER = Logger.getLogger("HubEssentials");

    @Override
    public void onEnable() {
        if (LOGGER != super.getLogger()) {
            LOGGER.setParent(super.getLogger());
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
