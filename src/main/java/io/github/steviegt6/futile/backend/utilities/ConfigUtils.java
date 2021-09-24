package io.github.steviegt6.futile.backend.utilities;

import io.github.steviegt6.futile.backend.savedata.ConfigOption;
import io.github.steviegt6.futile.backend.savedata.Configurable;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;

public final class ConfigUtils {
    public static void readConfig(Configurable config, FileConfiguration file) {
        Field[] fields = config.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (ConfigOption.class.isAssignableFrom(field.getType())) {
                try {
                    ConfigOption<?> option = (ConfigOption<?>) field.get(config);

                    // This handles setting cached values.
                    option.getValue(file);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void saveConfig(Configurable config, FileConfiguration file) {
        Field[] fields = config.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (ConfigOption.class.isAssignableFrom(field.getType())) {
                try {
                    ConfigOption<?> option = (ConfigOption<?>) field.get(config);

                    // This handles setting new values.
                    option.genericSet(file, option.Value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
