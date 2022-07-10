package net.cubehunt.hubessentials.utils;

import net.cubehunt.hubessentials.HubEssentials;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PluginVersion {

    private static final Logger logger = Logger.getLogger("HubEssentials");

    private final HubEssentials plugin;
    private final int ID;

    public PluginVersion(HubEssentials plugin, int ID) {
        this.plugin = plugin;
        this.ID = ID;
    }

    // Method that checks if the plugin is up to date
    public void check(final Consumer<String> consumer) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            try (
                    InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + ID).openStream();
                    Scanner scanner = new Scanner(inputStream)
            ) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException e) {
                logger.log(Level.WARNING, "Cannot look for updates: " + e.getMessage());
            }
        });
    }
}
