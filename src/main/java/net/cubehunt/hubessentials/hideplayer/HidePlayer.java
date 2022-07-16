package net.cubehunt.hubessentials.hideplayer;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Getter;
import net.cubehunt.hubessentials.HubEssentials;
import net.cubehunt.hubessentials.config.BaseConfiguration;
import net.cubehunt.hubessentials.config.IConfig;
import net.cubehunt.hubessentials.config.entities.LazyItem;
import net.cubehunt.hubessentials.utils.Color;
import net.cubehunt.hubessentials.utils.ItemBuilder;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static net.cubehunt.hubessentials.utils.Color.minimessageToLegacy;

public class HidePlayer implements IConfig {

    private final HubEssentials plugin;

    private final BaseConfiguration config;

    private LazyItem show;

    private LazyItem hide;

    @Getter
    private int slot;

    @Getter
    private int cooldownTime;

    @Getter
    private boolean applyHideStatusOnJoin;

    private Cache<UUID, Long> cooldown;

    public HidePlayer(HubEssentials plugin) {
        this.plugin = plugin;
        this.config = new BaseConfiguration(new File(plugin.getDataFolder(), "hide-players.yml"), "/hide-players.yml");
        reloadConfig();
    }

    public ItemStack showItem() {
        return new ItemBuilder(show.material())
                .setDisplayName(Color.minimessageToLegacy(show.name()))
                .setLore(minimessageToLegacy(show.lore()))
                .setPersistentDataContainerValue(plugin, "HIDE_STATUS", "SHOW")
                .toItemStack();
    }

    public ItemStack hideItem() {
        return new ItemBuilder(hide.material())
                .setDisplayName(Color.minimessageToLegacy(hide.name()))
                .setLore(minimessageToLegacy(hide.lore()))
                .setPersistentDataContainerValue(plugin, "HIDE_STATUS", "HIDE")
                .toItemStack();
    }

    public boolean inCooldown(final UUID uuid) {
        if (!cooldown.asMap().containsKey(uuid)) {
            cooldown.put(uuid, System.currentTimeMillis() + (cooldownTime * 1000L));
            return false;
        }
        return true;
    }

    public Long getRemainingSeconds(final UUID uuid) {
        return TimeUnit.MILLISECONDS.toSeconds(cooldown.asMap().get(uuid) - System.currentTimeMillis());
    }

    @Override
    public void reloadConfig() {
        config.load();
        this.show = config.getItem("show");
        this.hide = config.getItem("hide");
        this.slot = config.getInt("slot", 6);
        this.cooldownTime = config.getInt("cooldown", 3);
        this.applyHideStatusOnJoin = config.getBoolean("apply-hide-status-on-join", false);
        this.cooldown = CacheBuilder.newBuilder().expireAfterWrite(this.cooldownTime, TimeUnit.SECONDS).build();
    }
}
