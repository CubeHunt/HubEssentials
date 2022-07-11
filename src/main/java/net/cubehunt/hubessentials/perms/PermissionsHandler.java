package net.cubehunt.hubessentials.perms;

import net.cubehunt.hubessentials.HubEssentials;
import net.cubehunt.hubessentials.perms.impl.*;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PermissionsHandler implements IPermissionsHandler {

    private static final Logger logger = Logger.getLogger("HubEssentials");

    private final String defaultGroup = "default";
    private final HubEssentials plugin;
    private IPermissionsHandler handler = null;
    private boolean useSuperperms;

    private Class<?> lastHandler = null;

    public PermissionsHandler(final HubEssentials plugin, final boolean useSuperperms) {
        this.plugin = plugin;
        this.useSuperperms = useSuperperms;
    }

    @Override
    public String getGroup(final Player player) {
        String group = handler.getGroup(player);
        if (group == null) {
            group = defaultGroup;
        }
        return group;
    }

    @Override
    public List<String> getGroups(final Player player) {
        List<String> groups = handler.getGroups(player);
        if (groups == null || groups.isEmpty()) {
            groups = Collections.singletonList(defaultGroup);
        }
        return Collections.unmodifiableList(groups);
    }

    @Override
    public boolean inGroup(final Player player, final String group) {
        return handler.inGroup(player, group);
    }

    @Override
    public String getPrefix(final Player player) {
        String prefix = handler.getPrefix(player);
        if (prefix == null) {
            prefix = "";
        }
        return prefix;
    }

    @Override
    public String getSuffix(final Player player) {
        String suffix = handler.getSuffix(player);
        if (suffix == null) {
            suffix = "";
        }
        return suffix;
    }

    @Override
    public String getBackendName() {
        return handler.getBackendName();
    }

    @Override
    public boolean tryProvider(HubEssentials plugin) {
        return true;
    }

    public void checkPermissions() {
        final List<Class<? extends SuperpermsHandler>> providerClazz = Arrays.asList(
                LuckPermsHandler.class,
                ModernVaultHandler.class,
                GenericVaultHandler.class,
                SuperpermsHandler.class
        );
        for (final Class<? extends IPermissionsHandler> providerClass : providerClazz) {
            try {
                final IPermissionsHandler provider = providerClass.getDeclaredConstructor().newInstance();
                if (provider.tryProvider(plugin)) {
                    if (provider.getClass().isInstance(this.handler)) {
                        return;
                    }
                    this.handler = provider;
                    break;
                }
            } catch (final Throwable ignored) {
            }
        }
        if (handler == null) {
            if (useSuperperms) {
                handler = new SuperpermsHandler();
            }
        }

        final Class<?> handlerClass = handler.getClass();
        if (lastHandler != null && lastHandler == handlerClass) return;
        lastHandler = handlerClass;

        if (handler instanceof AbstractVaultHandler abstractVaultHandler) {
            String enabledPermsPlugin = abstractVaultHandler.getEnabledPermsPlugin();
            if (enabledPermsPlugin == null) enabledPermsPlugin = "generic";
            logger.log(Level.INFO, "Using Vault based permissions (" + enabledPermsPlugin + ")");
        } else if (handler.getClass() == SuperpermsHandler.class) {
            if (handler.tryProvider(plugin)) {
                logger.log(Level.WARNING, "Detected supported permissions plugin " +
                        ((SuperpermsHandler) handler).getEnabledPermsPlugin() + " without Vault installed.");
                logger.log(Level.WARNING, "Features such as chat prefixes/suffixes and group-related functionality will not " +
                        "work until you install Vault.");
            }
            logger.log(Level.INFO, "Using superperms-based permissions.");
        }
    }
}
