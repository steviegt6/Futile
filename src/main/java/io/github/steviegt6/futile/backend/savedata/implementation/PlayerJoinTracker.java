package io.github.steviegt6.futile.backend.savedata.implementation;

import io.github.steviegt6.futile.backend.savedata.Configurable;
import org.bukkit.configuration.file.FileConfiguration;

public class PlayerJoinTracker implements Configurable {
    public int Joins = 0;

    @Override
    public void readConfig(FileConfiguration config) {
        Joins = (int) config.get("player.joins", 0);
    }

    @Override
    public void saveConfig(FileConfiguration config) {
        config.set("player.joins", Joins);
    }
}
