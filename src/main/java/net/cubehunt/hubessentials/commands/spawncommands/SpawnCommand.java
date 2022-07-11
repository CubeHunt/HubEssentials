package net.cubehunt.hubessentials.commands.spawncommands;

import net.cubehunt.hubessentials.HubEssentials;
import net.cubehunt.hubessentials.commands.BaseCommand;
import net.cubehunt.hubessentials.commands.CommandSource;
import net.cubehunt.hubessentials.exceptions.InsufficientPermissionException;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

import static net.cubehunt.hubessentials.utils.Message.sendMessage;


public class SpawnCommand extends BaseCommand {

    private final HubEssentials plugin;

    public SpawnCommand(HubEssentials plugin) {
        super("spawn", "Teleports a player to the hub spawn.", "hubessentials.spawn", "hub");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSource sender, String[] args) throws Exception {
        if (!sender.isPlayer()) throw new Exception("<red>Only players can execute this command!");

        Player player = sender.getPlayer();

        if (!testPermissionSilent(player)) {
            throw new InsufficientPermissionException("<red>You do not have the permission for this command!");
        }

        if (!plugin.spawnExists()) {
            plugin.setSpawn(player.getWorld().getSpawnLocation());
        }
        player.teleport(plugin.getSpawn());

        sendMessage(player, "<green>You've been teleported to the spawn!");
    }

    @Override
    public List<String> onTabComplete(CommandSource sender, String[] args) {
        return Collections.emptyList();
    }
}
