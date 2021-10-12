package io.github.steviegt6.futile.backend.savedata.implementation;

import io.github.steviegt6.futile.backend.savedata.ConfigOption;
import io.github.steviegt6.futile.backend.savedata.Configurable;
import io.github.steviegt6.futile.backend.utilities.ConfigUtils;
import org.bukkit.configuration.file.FileConfiguration;

public class PlayerJoinTracker implements Configurable {
    public ConfigOption<Integer> Joins = new ConfigOption<>("player.joins", 0);
}
