package net.cubehunt.hubessentials;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class User {

    private final Player base;

    private final HubEssentials plugin;

    @Getter
    private String nickname;

    @Getter
    private Long loginTime;

    @Getter
    private Long logoutTime;

    @Getter
    private Long mutedTime;

    private final AtomicBoolean hideplayerStatus;

    public boolean getHideplayerStatus() {
        return hideplayerStatus.get();
    }

    public User(final UUID uuid, final HubEssentials plugin) {
        this(Bukkit.getPlayer(uuid), plugin);
    }

    public User(final Player base, final HubEssentials plugin) {
        this.base = base;
        this.plugin = plugin;

        if (!exists()) createNew();

        plugin.getUserData().updateName(base.getUniqueId(), base.getName());
        this.nickname = plugin.getUserData().getNickname(base.getUniqueId());
        this.loginTime = plugin.getUserData().getLoginTime(base.getUniqueId());
        this.logoutTime = plugin.getUserData().getLogoutTime(base.getUniqueId());
        this.mutedTime = plugin.getUserData().getMutedTime(base.getUniqueId());
        this.hideplayerStatus = new AtomicBoolean(plugin.getUserData().getHideStatus(base.getUniqueId()));
    }

//    ----- EXISTS -----------------------------------------------------------------------------------------------------

    public boolean exists() {
        return plugin.getUserData().exists(base.getUniqueId());
    }

//    ----- CREATE -----------------------------------------------------------------------------------------------------

    public void createNew() {
        plugin.getUserData().createNew(base);
    }

//    ----- NAME -------------------------------------------------------------------------------------------------------

    public void updateName() {

    }

//    ----- NICKNAME ---------------------------------------------------------------------------------------------------

    public void setNickname(final String newNickname) {
        this.nickname = newNickname;
        plugin.getUserData().setNickname(base.getUniqueId(), newNickname);
    }

//    ----- LOGIN ------------------------------------------------------------------------------------------------------

    public void setLoginTime(final Long newLoginTime) {
        this.loginTime = newLoginTime;
        plugin.getUserData().setLoginTime(base.getUniqueId(), newLoginTime);
    }

//    ----- LOGOUT -----------------------------------------------------------------------------------------------------

    public void setLogoutTime(final Long newLogoutTime) {
        this.logoutTime = newLogoutTime;
        plugin.getUserData().setLogoutTime(base.getUniqueId(), newLogoutTime);
    }

//    ----- MUTED ------------------------------------------------------------------------------------------------------

    public void setMutedTime(final Long newMutedTime) {
        this.mutedTime = System.currentTimeMillis() + newMutedTime;
        plugin.getUserData().setMutedTime(base.getUniqueId(), newMutedTime);
    }

//    ----- HIDE STATUS ------------------------------------------------------------------------------------------------

    public void updateHideStatus() {
        final boolean status = this.hideplayerStatus.get();
        this.hideplayerStatus.compareAndSet(status, !status);
        plugin.getUserData().setHideStatus(base.getUniqueId(), !status);
    }

//    ----- HIDE PLAYER FUNCTIONS --------------------------------------------------------------------------------------

    public void showPlayers() {
        updateHideStatus();
        for (final Player p : plugin.getServer().getOnlinePlayers()) {
            base.showPlayer(plugin, p);
        }
    }

    public void hidePlayers() {
        updateHideStatus();
        for (final Player p : plugin.getServer().getOnlinePlayers()) {
            base.hidePlayer(plugin, p);
        }
    }

















}
