package net.cubehunt.hubessentials;

import lombok.Getter;
import net.cubehunt.hubessentials.config.BaseConfiguration;
import net.cubehunt.hubessentials.config.IConfig;
import net.cubehunt.hubessentials.utils.Color;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;

public class Settings implements IConfig {

    private final HubEssentials plugin;
    private final BaseConfiguration config;

    @Getter
    private String chatFormat;
    @Getter
    private HashMap<String, String> nickColors = new HashMap<>();

    private String playerJoinMessage;

    private String playerQuitMessage;

    private String playerWelcomeMessage;

    public String getPlayerJoinMessage(final Player player) {
        String message = Color.minimessageToLegacy(playerJoinMessage);
        message = message.replace("{PLAYERNAME}", player.getName()).replace("{DISPLAYNAME}", player.getDisplayName());
        return message;
    }

    public String getPlayerQuitMessage(final Player player) {
        String message = Color.minimessageToLegacy(playerQuitMessage);
        message = message.replace("{PLAYERNAME}", player.getName()).replace("{DISPLAYNAME}", player.getDisplayName());
        return message;
    }

    public String getPlayerWelcomeMessage(final Player player) {
        String message = Color.minimessageToLegacy(playerWelcomeMessage);
        message = message.replace("{PLAYERNAME}", player.getName()).replace("{DISPLAYNAME}", player.getDisplayName());
        return message;
    }

    public Settings(HubEssentials plugin) {
        this.plugin = plugin;
        this.config = new BaseConfiguration(new File(plugin.getDataFolder(), "config.yml"), "/config.yml");
        reloadConfig();
    }

    @Override
    public void reloadConfig() {
        config.load();
        this.chatFormat = config.getString("chat.format", "");
        this.loadNickColors();
        this.playerJoinMessage = config.getString("chat.player-join-message", "");
        this.playerQuitMessage = config.getString("chat.player-quite-message", "");
        this.playerWelcomeMessage = config.getString("chat.player-welcome-message", "");
    }

    private void loadNickColors() {
        nickColors.clear();
        for (final String k : config.getKeys("chat.nick-colors")) {
            nickColors.put(k, config.getString("chat.nick-colors." + k, ""));
        }
    }
}
