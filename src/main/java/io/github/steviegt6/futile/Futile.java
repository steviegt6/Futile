package io.github.steviegt6.futile;

import com.destroystokyo.paper.utils.PaperPluginLogger;
import io.github.steviegt6.futile.backend.listeners.PluginListener;
import io.github.steviegt6.futile.backend.listeners.playerlisteners.JoinListener;
import io.github.steviegt6.futile.backend.singletons.Singleton;
import io.github.steviegt6.futile.backend.savedata.PlayerDataInstance;
import io.github.steviegt6.futile.backend.utilities.ThrowableUtils;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public final class Futile extends JavaPlugin {
    public static Singleton<Futile> INSTANCE = new Singleton<>();

    // Loaded data instance cache. Preferably retrieve using getPlayerConfig.
    public HashMap<String, PlayerDataInstance> LoadedPlayerData;

    public List<PluginListener> Listeners = List.of(new JoinListener());

    public Futile() {
        try {
            INSTANCE.setInstance(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        LoadedPlayerData = new HashMap<>();
    }

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

    // Save according to: dataFolder\players\playerUUIDValue.yml
    public String getPlayerConfigLoc(String playerUUID) {
        return playerUUID + ".yml";
    }

    @NotNull
    public Logger getLogger() {
        return PaperPluginLogger.getLogger(getDescription());
    }
}
