package io.github.steviegt6.futile;

import com.destroystokyo.paper.utils.PaperPluginLogger;
import io.github.steviegt6.futile.backend.listeners.PluginListener;
import io.github.steviegt6.futile.backend.listeners.playerlisteners.JoinListener;
import io.github.steviegt6.futile.backend.savedata.PlayerDataInstance;
import io.github.steviegt6.futile.backend.utilities.ThrowableUtils;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class Futile extends JavaPlugin {
    // Loaded data instance cache. Preferably retrieve using getPlayerConfig.
    public HashMap<String, PlayerDataInstance> LoadedPlayerData = new HashMap<>();

    public List<PluginListener> Listeners = Collections.singletonList(new JoinListener());

    @Override
    public void onEnable() {
        getLogger().info("Enabled Futile.");

        for (PluginListener listener : Listeners)
            listener.RegisterEvents(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled Futile.");
    }

    public PlayerDataInstance getPlayerConfig(String playerUUID) {
        if (LoadedPlayerData.containsKey(playerUUID))
            return LoadedPlayerData.get(playerUUID);

        File configFile = new File(getDataFolder(), getPlayerConfigLoc(playerUUID));

        if (!configFile.exists()) {
            getLogger().info("Making directories for player with UUID: " + playerUUID);
            boolean dirsMade = configFile.getParentFile().mkdirs();
            getLogger().info("Directory creation outcome: " + (dirsMade ? "Successful" : "Unsuccessful"));

            try {
                getLogger().info("Making file for player with UUID: " + playerUUID);
                boolean fileMade = configFile.createNewFile();
                getLogger().info("File creation outcome: " + (fileMade ? "Successful" : "Unsuccessful"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration yamlConfig = new YamlConfiguration();

        try {
            yamlConfig.load(configFile);
        } catch (IOException e) {
            getLogger().info("Could not find file for player with UUID: " + playerUUID);
        } catch (InvalidConfigurationException e) {
            getLogger().warning("Invalid configuration: " + ThrowableUtils.throwableToString(e));
        }

        LoadedPlayerData.put(playerUUID, new PlayerDataInstance(playerUUID, configFile, yamlConfig));

        return getPlayerConfig(playerUUID);
    }

    // Save according to: dataFolder\player-data\playerUUIDValue.yml
    public String getPlayerConfigLoc(String playerUUID) {
        return File.separatorChar + "player-data" + File.separatorChar + playerUUID + ".yml";
    }

    @NotNull
    public Logger getLogger() {
        return PaperPluginLogger.getLogger(getDescription());
    }

    public static Futile getPlugin() {
        return Futile.getPlugin(Futile.class);
    }
}
