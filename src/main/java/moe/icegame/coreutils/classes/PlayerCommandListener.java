package moe.icegame.coreutils.classes;

import moe.icegame.coreutils.GameUtil;
import moe.icegame.coreutils.ReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PlayerCommandListener implements Listener {
    private final Map<String, PlayerCommandHandler> handlers = new HashMap<>();

    public PlayerCommandListener(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public boolean HandlerExisting(CommandSender sender, Command command, String label, String[] args) {
        if (handlers.containsKey(label)) {
            boolean isConsole = sender instanceof ConsoleCommandSender;
            handlers.get(label).Handle(
                    sender,
                    new LintedCommand(GameUtil.FullCommand(command, args)),
                    !isConsole,
                    isConsole ? null : (Player) sender
            );
        }
        return true;
    }

    public void RegisterHandler(PlayerCommandHandler handler) {
        handlers.put(handler.cmd, handler);
        Bukkit.getLogger().info(String.format("Registered command %s", handler.cmd));
    }

    public static abstract class PlayerCommandHandler {
        public final String cmd;
        public PlayerCommandHandler(String cmd) {
            this.cmd = cmd;
        }
        public abstract void Handle(CommandSender sender, LintedCommand cmd, boolean isPlayer, Player player);
    }
}
