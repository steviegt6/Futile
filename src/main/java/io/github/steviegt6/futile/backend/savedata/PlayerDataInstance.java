package io.github.steviegt6.futile.backend.savedata;

import io.github.steviegt6.futile.backend.savedata.implementation.PlayerJoinTracker;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
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

    @SneakyThrows
    public void saveData() {
        Config.set("player.uuid", PlayerUUID);

        for (Configurable config : SaveData.values())
            config.saveConfig(Config);

        Config.save(ConfigFile);
    }

    public <T> T getRequired(Class<T> type) throws Exception {
        for (Configurable config : SaveData.values())
            if (type.isInstance(config))
                return (T) config;

        throw new Exception();
    }
}
