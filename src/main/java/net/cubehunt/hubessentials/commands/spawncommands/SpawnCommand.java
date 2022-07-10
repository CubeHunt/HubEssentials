package net.cubehunt.hubessentials.commands.spawncommands;

import net.cubehunt.hubessentials.HubEssentials;
import net.cubehunt.hubessentials.commands.BaseCommand;
import net.cubehunt.hubessentials.commands.CommandSource;
import net.cubehunt.hubessentials.exceptions.InsufficientPermissionException;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

import static net.cubehunt.hubessentials.utils.Color.colorize;


public class SpawnCommand extends BaseCommand {

    private final HubEssentials plugin;

    // Setting command info
    public SpawnCommand(HubEssentials plugin) {
        super("spawn", "Teleports a player to the hub spawn.", "hubessentials.spawn", "hub");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSource sender, String[] args) throws Exception {

        // Se il sender non è un player
        if (!sender.isPlayer()) throw new Exception("<red>Only players can execute this command!");

        Player player = sender.getPlayer();

        // Se il player non possiede il permesso hubessentials.spawn
        if (!testPermissionSilent(player)) {
            throw new InsufficientPermissionException("<red>You do not have the permission for this command!");
        }

        // Se lo spawn ancora non è stato settato quando si fa /spawn, viene automaticamente impostato lo spawn di default del mondo
        if (!plugin.spawnExists()) {
            plugin.setSpawn(player.getWorld().getSpawnLocation());
        }

        // Teletrasporto il player allo spawn
        player.teleport(plugin.getSpawn());

        // Altrimenti se lo possiede
        player.spigot().sendMessage(colorize("<green>You've been teleported to the spawn!"));
    }

    @Override
    public List<String> onTabComplete(CommandSource sender, String[] args) {
        return Collections.emptyList();
    }
}
