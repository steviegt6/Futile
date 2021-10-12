package io.github.steviegt6.futile.backend.listeners;

import io.github.steviegt6.futile.Futile;

public abstract class BasicListener implements PluginListener {
    public final Futile Plugin;

    public BasicListener(Futile plugin) {
        Plugin = plugin;
    }
}
