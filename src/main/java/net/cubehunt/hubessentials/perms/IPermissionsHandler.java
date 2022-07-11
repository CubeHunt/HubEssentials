package net.cubehunt.hubessentials.perms;

import net.cubehunt.hubessentials.HubEssentials;
import org.bukkit.entity.Player;

import java.util.List;

public interface IPermissionsHandler {

    String getGroup(Player player);

    List<String> getGroups(Player player);

    boolean inGroup(Player player, String group);

    String getPrefix(Player player);

    String getSuffix(Player player);

    String getBackendName();

    boolean tryProvider(HubEssentials plugin);

}
