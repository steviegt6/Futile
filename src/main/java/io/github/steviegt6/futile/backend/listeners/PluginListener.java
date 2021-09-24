package io.github.steviegt6.futile.backend.listeners;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public interface PluginListener extends Listener {
    void RegisterEvents(Plugin plugin);
}
