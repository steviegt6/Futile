package io.github.steviegt6.futile.backend.listeners.playerlisteners;

import io.github.steviegt6.futile.Futile;
import io.github.steviegt6.futile.backend.listeners.BasicListener;
import io.github.steviegt6.futile.backend.savedata.PlayerDataInstance;
import io.github.steviegt6.futile.backend.savedata.implementation.PlayerJoinTracker;
import lombok.SneakyThrows;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class JoinListener extends BasicListener {
    @SneakyThrows
    @Override
    public void RegisterEvents(Plugin plugin) {
        Futile.INSTANCE.getInstance().getLogger().info("Registering listeners in JoinListener.");
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    // Typically don't wanna set to highest but this is important for handling data...
    @SneakyThrows
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent evt) {
        String joinUUID = evt.getPlayer().getUniqueId().toString();

        PlayerDataInstance data = Futile.INSTANCE.getInstance().getPlayerConfig(joinUUID);
        PlayerJoinTracker tracker = data.getRequired(PlayerJoinTracker.class);
        data.readData();

        String playerUUID = (String) data.Config.get("player.uuid");

        // Just gonna log this for now.
        assert playerUUID != null;
        if (!playerUUID.equals(joinUUID))
            Futile.INSTANCE.getInstance().getLogger().warning("Mismatch between player UUIDs: " + playerUUID + " and " + joinUUID);

        tracker.Joins++;

        Futile.INSTANCE.getInstance().getLogger().info(String.format("Player joined with data: Name %s, UUID %s, Join Count (after addition) %s", evt.getPlayer().displayName(), joinUUID, tracker.Joins));
    }

    @SneakyThrows
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent evt) {
        Futile.INSTANCE.getInstance().getPlayerConfig(evt.getPlayer().getUniqueId().toString()).saveData();
    }
}
