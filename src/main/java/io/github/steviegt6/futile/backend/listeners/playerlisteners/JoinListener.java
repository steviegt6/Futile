package io.github.steviegt6.futile.backend.listeners.playerlisteners;

import io.github.steviegt6.futile.Futile;
import io.github.steviegt6.futile.backend.listeners.BasicListener;
import io.github.steviegt6.futile.backend.savedata.PlayerDataInstance;
import io.github.steviegt6.futile.backend.savedata.implementation.PlayerJoinTracker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class JoinListener extends BasicListener {
    public JoinListener(Futile plugin) {
        super(plugin);
    }

    @Override
    public void RegisterEvents(Plugin plugin) {
        Plugin.getLogger().info("Registering listeners in JoinListener.");
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    // Typically don't wanna set to highest but this is important for handling data...
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent evt) {
        String joinUUID = evt.getPlayer().getUniqueId().toString();
        PlayerDataInstance data = Plugin.getPlayerConfig(joinUUID);
        PlayerJoinTracker tracker = data.getRequired(PlayerJoinTracker.class);

        data.readData();

        String playerUUID = (String) data.Config.get("player.uuid");

        // Just gonna log this for now.
        if (playerUUID != null && !playerUUID.equals(joinUUID)) {
            Plugin.getLogger().warning("Mismatch between player UUIDs: " + playerUUID + " and " + joinUUID);
        }

        if (tracker == null)
            return;

        tracker.Joins.Value++;
        Plugin.getLogger().info("Player joined with data: Name " + evt.getPlayer().displayName().examinableName() + ", UUID " + joinUUID + ", Join Count (after addition) " + tracker.Joins.Value);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent evt) {
        Plugin.getPlayerConfig(evt.getPlayer().getUniqueId().toString()).saveData();
    }
}
