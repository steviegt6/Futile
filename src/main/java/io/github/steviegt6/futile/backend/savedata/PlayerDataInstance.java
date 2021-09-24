package io.github.steviegt6.futile.backend.savedata;

import io.github.steviegt6.futile.backend.savedata.implementation.PlayerJoinTracker;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public final class PlayerDataInstance {
    public Map<String, Configurable> SaveData = Map.of("PlayerJoinTracker", new PlayerJoinTracker());

    public String PlayerUUID;
    public File ConfigFile;
    public FileConfiguration Config;

    public PlayerDataInstance(String playerUUID, File file, FileConfiguration config) {
        PlayerUUID = playerUUID;
        ConfigFile = file;
        Config = config;
    }

    public void readData() {
        for (Configurable config : SaveData.values())
            config.readConfig(Config);
    }

    public void saveData() {
        Config.set("player.uuid", PlayerUUID);

        for (Configurable config : SaveData.values())
            config.saveConfig(Config);

        try {
            Config.save(ConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getRequired(Class<T> type) {
        for (Configurable config : SaveData.values())
            if (type.isInstance(config))
                return (T) config;

        return null;
    }
}
