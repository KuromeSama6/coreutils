package moe.icegame.coreutils.classes.yaml;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class YamlWriteSession implements AutoCloseable{
    private final Consumer<YamlConfiguration> onClose;
    private final File file;
    private YamlConfiguration cfg;
    public YamlWriteSession(File file, Consumer<YamlConfiguration> onClose) {
        this.file = file;
        this.onClose = onClose;

        cfg = YamlConfiguration.loadConfiguration(file);
    }

    public YamlConfiguration getCfg() {
        return cfg;
    }

    @Override
    public void close() {
        try {
            cfg.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (onClose != null) onClose.accept(cfg);

    }
}
