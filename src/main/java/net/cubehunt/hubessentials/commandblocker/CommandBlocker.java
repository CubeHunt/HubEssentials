package net.cubehunt.hubessentials.commandblocker;

import lombok.Getter;
import net.cubehunt.hubessentials.HubEssentials;
import net.cubehunt.hubessentials.config.BaseConfiguration;
import net.cubehunt.hubessentials.config.IConfig;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class CommandBlocker implements IConfig {

    private final HubEssentials plugin;

    public CommandBlocker(HubEssentials plugin) {
        this.plugin = plugin;
    }

    @Override
    public void reloadConfig() {
        config.load();
        registerBlockedCommands(); // In modo che al reload i comandi vengano riregistrati
    }

    // Metodo che legge il file.yml popolando il set con i vari comandi bloccati
    private void registerBlockedCommands() {
        blockedCommands.clear();
        Set<String> keys = config.getKeys("commands");

        for (final String key : keys) {
            final String permission = config.getString("commands." + key + ".permission", "");
            final String message = config.getString("commands." + key + ".message", "");
            blockedCommands.add(new CommandToBlock(key, permission, message));
        }
    }
}
