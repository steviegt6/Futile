package io.github.steviegt6.futile.backend.savedata;

import org.bukkit.configuration.file.FileConfiguration;

public interface Configurable {
    void readConfig(FileConfiguration config);

    void saveConfig(FileConfiguration config);
}
