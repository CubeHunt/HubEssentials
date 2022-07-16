package net.cubehunt.hubessentials;

import lombok.Getter;
import net.cubehunt.hubessentials.utils.Color;
import net.cubehunt.hubessentials.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class User extends ExpandedPlayer {

    private final HubEssentials plugin;

    @Getter
    private String nickname;

    @Getter
    private Long loginTime;

    @Getter
    private Long logoutTime;

    private final AtomicBoolean hideStatus;

    public boolean getHideStatus() {
        return hideStatus.get();
    }

    public User(final UUID uuid, final HubEssentials plugin) {
        this(Bukkit.getPlayer(uuid), plugin);
    }

    public User(final Player player, final HubEssentials plugin) {
        super(player);
        this.plugin = plugin;

        if (!exists()) createNew();

        plugin.getUserData().updateName(player.getUniqueId(), player.getName());
        this.nickname = plugin.getUserData().getNickname(player.getUniqueId());
        this.loginTime = plugin.getUserData().getLoginTime(player.getUniqueId());
        this.logoutTime = plugin.getUserData().getLogoutTime(player.getUniqueId());
        this.hideStatus = new AtomicBoolean(plugin.getUserData().getHideStatus(player.getUniqueId()));
    }

    public void sendMessage(final String message) {
        Message.sendMessage(player, message);
    }

    public boolean hasPermission(final String perm) {
        return player.hasPermission(perm);
    }

    public String getPrefix() {
        return plugin.getPermissionsHandler().getPrefix(player);
    }

    public String getSuffix() {
        return plugin.getPermissionsHandler().getSuffix(player);
    }

//    ----- EXISTS -----------------------------------------------------------------------------------------------------

    public boolean exists() {
        return plugin.getUserData().exists(player.getUniqueId());
    }

//    ----- CREATE -----------------------------------------------------------------------------------------------------

    public void createNew() {
        plugin.getUserData().createNew(player);
    }

//    ----- NAME -------------------------------------------------------------------------------------------------------

    public void updateName() {

    }

//    ----- NICKNAME ---------------------------------------------------------------------------------------------------

    public String getNickname() {
        return Color.legacy(plugin.getPermissionsHandler().getPrefix(player)) + Color.minimessageToLegacy(this.nickname);
    }

    public void setNickname() {
        this.player.setDisplayName(getNickname());
        this.player.setPlayerListName(getNickname());
    }

    public void updateNickname(final String newNickname) {
        this.nickname = newNickname;
        plugin.getUserData().setNickname(player.getUniqueId(), newNickname);
        setNickname();
    }

//    ----- LOGIN ------------------------------------------------------------------------------------------------------

    public void setLoginTime(final Long newLoginTime) {
        this.loginTime = newLoginTime;
        plugin.getUserData().setLoginTime(player.getUniqueId(), newLoginTime);
    }

//    ----- LOGOUT -----------------------------------------------------------------------------------------------------

    public void setLogoutTime(final Long newLogoutTime) {
        this.logoutTime = newLogoutTime;
        plugin.getUserData().setLogoutTime(player.getUniqueId(), newLogoutTime);
    }

//    ----- HIDE STATUS ------------------------------------------------------------------------------------------------

    public void updateHideStatus() {
        final boolean status = this.hideStatus.get();
        this.hideStatus.compareAndSet(status, !status);
        plugin.getUserData().setHideStatus(player.getUniqueId(), !status);
    }

//    ----- HIDE PLAYER FUNCTIONS --------------------------------------------------------------------------------------

    public void showPlayers() {
        for (final Player p : plugin.getServer().getOnlinePlayers()) {
            player.showPlayer(plugin, p);
        }
    }

    public void hidePlayers() {
        for (final Player p : plugin.getServer().getOnlinePlayers()) {
            player.hidePlayer(plugin, p);
        }
    }

















}
