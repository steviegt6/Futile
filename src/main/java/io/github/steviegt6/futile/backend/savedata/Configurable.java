package io.github.steviegt6.futile.backend.savedata;

import io.github.steviegt6.futile.backend.utilities.ConfigUtils;
import org.bukkit.configuration.file.FileConfiguration;

public interface Configurable {
    default void readConfig(FileConfiguration config) {
        ConfigUtils.readConfig(this, config);
    }

    default void saveConfig(FileConfiguration config) {
        ConfigUtils.saveConfig(this, config);
    }
}
