package moe.icegame.coreutils.classes;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class PooledScheduler {
    private final JavaPlugin plugin;
    @Getter private List<Integer> handles = new ArrayList<>();

    public PooledScheduler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void Add(Runnable func, long delay) {
        handles.add(Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, func, delay));
    }

    public void AddRepeating(Runnable func, long delay, long interval) {
        handles.add(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, func, delay, interval));
    }

    public void JoinMain(Runnable func) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, func, 0);
    }

    public void Free() {
        for (int handle : handles) Bukkit.getScheduler().cancelTask(handle);
    }

}
