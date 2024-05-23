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

    public int Add(Runnable func, long delay) {
        int handle = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, func, delay);
        handles.add(handle);
        return handle;
    }

    public int AddRepeating(Runnable func, long delay, long interval) {
        int handle = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, func, delay, interval);
        handles.add(handle);
        return handle;
    }

    public void JoinMain(Runnable func) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, func, 0);
    }

    public void Free() {
        for (int handle : handles) Bukkit.getScheduler().cancelTask(handle);
    }

    public void Cancel(int handle) {
        Bukkit.getScheduler().cancelTask(handle);
        handles.remove((Object)handle);
    }

}
