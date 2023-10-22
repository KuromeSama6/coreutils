package moe.icegame.coreutils;

import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeMain extends Plugin {
    @Getter
    private static BungeeMain instance;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("starting coreutils (bungeecord1)");
    }
}
