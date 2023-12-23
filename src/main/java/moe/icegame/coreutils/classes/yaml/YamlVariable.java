package moe.icegame.coreutils.classes.yaml;

import org.bukkit.configuration.ConfigurationSection;

public class YamlVariable<T> {
    private final ConfigurationSection cfg;
    private final String key;

    public YamlVariable(ConfigurationSection cfg, String key) {
        this.cfg = cfg;
        this.key = key;
    }

    public T get() {
        return (T)cfg.get(key);
    }

    public T get(T def) {
        return (T)cfg.get(key, def);
    }

    public void set(T val) {
        cfg.set(key, val);
    }

    public void erase() {
        cfg.set(key, null);
    }

}
