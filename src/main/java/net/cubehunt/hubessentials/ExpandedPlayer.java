package net.cubehunt.hubessentials;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class ExpandedPlayer {

    @Getter
    @Setter
    protected Player player;

    public ExpandedPlayer(Player player) {
        this.player = player;
    }

    public Server getServer() {
        return player.getServer();
    }

    public World getWorld() {
        return player.getWorld();
    }

    public Location getLocation() {
        return player.getLocation();
    }
}
