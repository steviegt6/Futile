package io.github.steviegt6.futile.backend.savedata;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigOption<T> {
    public String Option;
    public T Default;

    public T Value;

    public ConfigOption(String option, T def) {
        Option = option;
        Default = def;
    }

    // Important to note that this retrieves from the config. get() retrieves from Value.
    @SuppressWarnings("unchecked")
    public T getValue(FileConfiguration config) {
        return Value = (T) config.get(Option, Default);
    }

    public void setValue(FileConfiguration config, T value) {
        config.set(Option, value);
    }

    // For <?> generics. :/
    @SuppressWarnings("unchecked")
    public void genericSet(FileConfiguration config, Object o) {
        setValue(config, (T) o);
    }
}
