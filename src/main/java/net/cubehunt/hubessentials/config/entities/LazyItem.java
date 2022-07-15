package net.cubehunt.hubessentials.config.entities;

import org.bukkit.Material;

import java.util.List;

public record LazyItem(Material material, String name, List<String> lore) {

}
