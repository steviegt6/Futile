package io.github.steviegt6.futile.backend.savedata.implementation;

import io.github.steviegt6.futile.backend.savedata.ConfigOption;
import io.github.steviegt6.futile.backend.savedata.Configurable;
import io.github.steviegt6.futile.backend.utilities.ConfigUtils;
import org.bukkit.configuration.file.FileConfiguration;

public class PlayerJoinTracker implements Configurable {
    public ConfigOption<Integer> Joins = new ConfigOption<>("player.joins", 0);

    @Override
    public void readConfig(FileConfiguration config) {
        ConfigUtils.readConfig(this, config);
    }

    @Override
    public void saveConfig(FileConfiguration config) {
        ConfigUtils.saveConfig(this, config);
    }
}
