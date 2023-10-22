package moe.icegame.coreutils;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotMain extends JavaPlugin {
    @Getter private static SpigotMain instance;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("starting coreutils");
    }
}
