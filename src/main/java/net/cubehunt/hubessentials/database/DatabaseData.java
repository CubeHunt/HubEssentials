package net.cubehunt.hubessentials.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import net.cubehunt.hubessentials.HubEssentials;
import net.cubehunt.hubessentials.config.BaseConfiguration;
import net.cubehunt.hubessentials.config.IConfig;

import java.io.File;

public class DatabaseData implements IConfig {

    private final HubEssentials plugin;

    private final BaseConfiguration config;

    @Getter
    private HikariDataSource dbSource;

    @Getter
    private StorageType storageType;

    public DatabaseData(HubEssentials plugin) {
        this.plugin = plugin;
        this.config = new BaseConfiguration(new File(plugin.getDataFolder(), "sql.yml"), "/sql.yml");
        reloadConfig();
    }

    private void createConnection() {
        final HikariConfig hConfig = new HikariConfig();
        hConfig.setPoolName("HubEssentials");

        StorageType type;
        try {
            type = StorageType.valueOf(this.config.getString("storage.type", "h2").toUpperCase());
        } catch (IllegalArgumentException e) {
            type = StorageType.H2;
        }
        this.storageType = type;

        hConfig.setDriverClassName(type.driverClass);

        if (type.remote) {
            hConfig.setJdbcUrl(
                    String.format(
                            "jdbc:%s://%s:%s/%s",
                            type.name().toLowerCase(),
                            this.config.getString("storage.settings.remote-db.host", "localhost"),
                            this.config.getString("storage.settings.remote-db.port", "3306"),
                            this.config.getString("storage.settings.remote-db.database", "hubessentials")
                    )
            );
            hConfig.setUsername(config.getString("storage.settings.remote-db.username", "root"));
            hConfig.setPassword(config.getString("storage.settings.remote-db.password", ""));

        } else {
            String dbFile = plugin.getDataFolder().getAbsolutePath() + "/";
            if (type == StorageType.SQLITE) dbFile += config.getString("storage.settings.sqlite", "hubessentials.db");
            if (type == StorageType.H2) dbFile += config.getString("storage.settings.h2", "hubessentials");

            hConfig.setJdbcUrl(String.format("jdbc:%s:%s", type.name().toLowerCase(), dbFile));
        }

        dbSource = new HikariDataSource(hConfig);
    }

    @Override
    public void reloadConfig() {
        close();
        config.load();
        createConnection();
    }

    private enum StorageType {
        MYSQL("com.mysql.cj.jdbc.Driver", true),
        MARIADB("org.mariadb.jdbc.Driver", true),
        POSTGRESQL("org.postgresql.Driver", true),
        SQLITE("org.sqlite.JDBC", false),
        H2("org.h2.Driver", false);

        private final String driverClass;

        private final boolean remote;

        StorageType(String driverClass, boolean remote) {
            this.driverClass = driverClass;
            this.remote = remote;
        }
    }

    public void close() {
        if (dbSource != null) {
            dbSource.close();
        }
    }
}
