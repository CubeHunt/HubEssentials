package net.cubehunt.hubessentials.database;

import net.cubehunt.hubessentials.HubEssentials;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserData {

    private final HubEssentials plugin;

    public UserData(HubEssentials plugin) {
        this.plugin = plugin;
        _init();
    }

    private static String USERS_TABLE = "CREATE TABLE IF NOT EXISTS `users` (" +
            " `uuid` VARCHAR(36)," +
            " `name` VARCHAR(50) NOT NULL," +
            " `nickname` VARCHAR(50)," +
            " `login` BIGINT," +
            " `logout` BIGINT," +
            " `muted` BIGINT," +
            " `hide_status` BOOLEAN," +
            " PRIMARY KEY (`uuid`))";

    private void _init() {
        try (final Connection conn = plugin.getConnection();
             PreparedStatement ps = conn.prepareStatement(USERS_TABLE)
        ) {
            ps.executeUpdate();
        } catch (final SQLException ignored) {
        }
    }

//    ----- EXISTS -----------------------------------------------------------------------------------------------------

    public boolean exists(final UUID uuid) {
        try (Connection conn = plugin.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE uuid=?")) {
            ps.setString(1, uuid.toString());
            return ps.executeQuery().next();
        } catch (final SQLException ex) {
            return false;
        }
    }

//    ----- CREATE -----------------------------------------------------------------------------------------------------

    public void createNew(final Player player) {
        try (Connection conn = plugin.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO users (uuid, name, nickname, login, logout, muted, hide_status) VALUES(?,?,?,?,?,?,?)")) {
            ps.setString(1, player.getUniqueId().toString());
            ps.setString(2, player.getName());
            ps.setString(3, player.getName());
            ps.setLong(4, System.currentTimeMillis());
            ps.setLong(5, 0L);
            ps.setBoolean(6, false);
            ps.setBoolean(7, false);
            ps.executeUpdate();
        } catch (final SQLException ex) {
            ex.printStackTrace();
        }
    }

//    ----- NAME -------------------------------------------------------------------------------------------------------

    public void updateName(final UUID uuid, final String newName) {
        try (Connection conn = plugin.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE users SET name=? WHERE uuid=?")
        ) {
            ps.setString(1, newName.toLowerCase());
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (final SQLException ignored) {
        }
    }

//    ----- NICKNAME ---------------------------------------------------------------------------------------------------

    public String getNickname(final UUID uuid) {
        try (Connection conn = plugin.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT nickname FROM users WHERE uuid=?")
        ) {
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("nickname");
        } catch (final SQLException ignored) {
        }
        return "";
    }

    public void setNickname(final UUID uuid, final String newNickname) {
        try (Connection conn = plugin.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE users SET nickname=? WHERE uuid=?")
        ) {
            ps.setString(1, newNickname.toLowerCase());
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (final SQLException ignored) {
        }
    }

//    ----- LOGIN ------------------------------------------------------------------------------------------------------

    public Long getLoginTime(final UUID uuid) {
        try (Connection conn = plugin.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT login FROM users WHERE uuid=?")
        ) {
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getLong("login");
        } catch (final SQLException ignored) {
        }
        return 0L;
    }

    public void setLoginTime(final UUID uuid, final Long newLoginTime) {
        try (Connection conn = plugin.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE users SET login=? WHERE uuid=?")
        ) {
            ps.setLong(1, newLoginTime);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (final SQLException ignored) {
        }
    }

//    ----- LOGOUT -----------------------------------------------------------------------------------------------------

    public Long getLogoutTime(final UUID uuid) {
        try (Connection conn = plugin.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT logout FROM users WHERE uuid=?")
        ) {
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getLong("logout");
        } catch (final SQLException ignored) {
        }
        return 0L;
    }

    public void setLogoutTime(final UUID uuid, final Long newLogoutTime) {
        try (Connection conn = plugin.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE users SET logout=? WHERE uuid=?")
        ) {
            ps.setLong(1, newLogoutTime);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (final SQLException ignored) {
        }
    }

//    ----- MUTED ------------------------------------------------------------------------------------------------------

    public Long getMutedTime(final UUID uuid) {
        try (Connection conn = plugin.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT muted FROM users WHERE uuid=?")
        ) {
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getLong("muted");
        } catch (final SQLException ignored) {
        }
        return 0L;
    }

    public void setMutedTime(final UUID uuid, final Long newMutedTime) {
        try (Connection conn = plugin.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE users SET muted=? WHERE uuid=?")
        ) {
            ps.setLong(1, System.currentTimeMillis() + newMutedTime);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (final SQLException ignored) {
        }
    }

//    ----- HIDE STATUS ------------------------------------------------------------------------------------------------

    public Boolean getHideStatus(final UUID uuid) {
        try (Connection conn = plugin.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT hide_status FROM users WHERE uuid=?")
        ) {
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getBoolean("hide_status");
        } catch (final SQLException ignored) {
        }
        return false;
    }

    public void setHideStatus(final UUID uuid, final Boolean newHideStatus) {
        try (Connection conn = plugin.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE users SET hide_status=? WHERE uuid=?")
        ) {
            ps.setBoolean(1, newHideStatus);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (final SQLException ignored) {
        }
    }










}
