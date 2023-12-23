package moe.icegame.coreutils;

import com.github.f4b6a3.uuid.UuidCreator;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.function.Predicate;

public class GameUtil {
    public static boolean CommandHasPermission(CommandSender sender, Command cmd){
        return sender instanceof ConsoleCommandSender || cmd.getPermission() == null || sender.hasPermission(cmd.getPermission());
    }

    public static String FullCommand(Command cmd, String[] args) {
        return cmd.getLabel() + " " + String.join(" ", args);
    }

    public static Player[] FindAllWithPerm(JavaPlugin pl, String perm) {
        List<Player> ret = new ArrayList<>();
        for (Player player : pl.getServer().getOnlinePlayers()) {
            if (player.hasPermission(perm)) ret.add(player);
        }

        return ret.toArray(new Player[0]);
    }

    public static void SetInterval(JavaPlugin pl, Runnable func, int ms){
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(pl, func, 0L, ms / 1000 * 20);
    }

    public static YamlConfiguration UpdateCofig(File dataFolder, Object pl, String relPath) {
        if (!Files.exists(Paths.get(dataFolder.getAbsolutePath()))) {
            try {
                Files.createDirectory(Paths.get(dataFolder.getAbsolutePath()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (!Files.exists(Paths.get(dataFolder.getAbsolutePath() + relPath))) {
            String resource = DevUtil.ReadResourceFile(pl.getClass(), relPath.substring(1));
            Path path = Paths.get(dataFolder.getAbsolutePath() + relPath);
            try {
                Files.write(path, Collections.singleton(resource), StandardCharsets.UTF_8, StandardOpenOption.CREATE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            if(new File(dataFolder + relPath).exists()) {
                boolean changesMade = false;
                YamlConfiguration current = new YamlConfiguration();
                current.load(dataFolder + relPath);

                YamlConfiguration newest = new YamlConfiguration();
                newest.loadFromString(DevUtil.ReadResourceFile(pl.getClass(), relPath.substring(1)));

                for(String str : newest.getKeys(true)) {
                    if(!current.getKeys(true).contains(str)) {
                        current.set(str, newest.get(str));
                        changesMade = true;
                    }
                }

                if (current.getKeys(false).contains("__p")) {
                    current.set("__p", null);
                    changesMade = true;
                }

                if (changesMade) current.save(dataFolder + relPath);

                return current;
            }
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static YamlConfiguration ReadConfigFromString(String in) {
        YamlConfiguration ret = new YamlConfiguration();
        try {
            ret.loadFromString(in);
            return ret;
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static YamlConfiguration UpdateCofig(JavaPlugin pl, String relPath) {
        return UpdateCofig(pl.getDataFolder(), pl, relPath);
    }

    public static YamlConfiguration UpdateConfigWithDefaults(JavaPlugin pl, String relPath) {
        return UpdateConfigWithDefaults(pl.getDataFolder(), pl, relPath, relPath);
    }

    public static YamlConfiguration UpdateConfigWithDefaults(File dataFolder, Object pl, String relPath, String outPath) {
        YamlConfiguration ret = new YamlConfiguration();
        YamlConfiguration template = new YamlConfiguration();
        try {
            String content = DevUtil.ReadResourceFile(pl.getClass(), relPath.substring(1));
            try {
                template.loadFromString(content);
            } catch (InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }

            ret.addDefaults(template);

            File pth = new File(dataFolder + outPath);
            if (!pth.exists()) {
                // remove an empty file
                if (template.getKeys(true).isEmpty()) {
                    template.set("__p", 0);
                }
                template.save(pth);
            }
            ret.load(pth);
            // System.out.println(ret.saveToString());
            ret.save(pth);
            return ret;

        } catch (InvalidConfigurationException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static YamlConfiguration UpdateCofig(File dataFolder, Object pl, String relPath, String outPath) {
        if (!Files.exists(Paths.get(dataFolder.getAbsolutePath()), new LinkOption[0])) {
            try {
                Files.createDirectory(Paths.get(dataFolder.getAbsolutePath()));
            } catch (IOException var9) {
                throw new RuntimeException(var9);
            }
        }

        if (!Files.exists(Paths.get(dataFolder.getAbsolutePath() + outPath), new LinkOption[0])) {
            String resource = DevUtil.ReadResourceFile(pl.getClass(), relPath.substring(1));
            Path path = Paths.get(dataFolder.getAbsolutePath() + outPath);

            try {
                Files.write(path, Collections.singleton(resource), StandardCharsets.UTF_8, StandardOpenOption.CREATE);
            } catch (IOException var8) {
                throw new RuntimeException(var8);
            }
        }

        try {
            if ((new File(dataFolder + outPath)).exists()) {
                boolean changesMade = false;
                YamlConfiguration current = new YamlConfiguration();
                current.load(dataFolder + outPath);
                YamlConfiguration newest = new YamlConfiguration();
                newest.loadFromString(DevUtil.ReadResourceFile(pl.getClass(), relPath.substring(1)));
                Iterator var6 = newest.getKeys(true).iterator();

                while(var6.hasNext()) {
                    String str = (String)var6.next();
                    if (!current.getKeys(true).contains(str)) {
                        current.set(str, newest.get(str));
                        changesMade = true;
                    }
                }

                if (current.getKeys(false).contains("__p")) {
                    current.set("__p", (Object)null);
                    changesMade = true;
                }

                if (changesMade) {
                    current.save(dataFolder + outPath);
                }

                return current;
            }
        } catch (InvalidConfigurationException | IOException var10) {
            var10.printStackTrace();
        }

        return null;
    }

    public static Vector TranslateRelativeVelocity(Vector knockback, Player target,  Player attacker) {
        return new Vector(
                (target.getLocation().getX() - attacker.getLocation().getX()) * knockback.getX(),
                knockback.getY(),
                (target.getLocation().getZ() - attacker.getLocation().getZ()) * knockback.getZ());
    }

    public static Location DeserializeLocation(ConfigurationSection cfg) {
        try {
            World world = Bukkit.getServer().getWorld(cfg.getString("world"));
            List<Double> coords = cfg.getDoubleList("pos");
            return new Location(world, coords.get(0), coords.get(1), coords.get(2));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Location DeserializeLocation(World world, List<Double> coords) {
        try {
            return new Location(world, coords.get(0), coords.get(1), coords.get(2));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static YamlConfiguration SerializeLocation(Location loc) {
        YamlConfiguration ret = new YamlConfiguration();
        ret.set("world", loc.getWorld().getName());
        ret.set("pos", new Double[]{loc.getX(), loc.getY(), loc.getZ()});

        return ret;
    }

    public static Double[] SerializePosition(Location loc) {
        return new Double[]{loc.getX(), loc.getY(), loc.getZ()};
    }

    public static int CountItems(Inventory inventory, Predicate<ItemStack> pred) {
        int ret = 0;
        for (ItemStack item : inventory) if (pred.test(item)) ret++;
        return ret;
    }

    public static int CountItems(Inventory inventory, Material material){
        return CountItems(inventory, c -> c.getType() == material);
    }

    public static int ClearItems(Inventory inventory, Predicate<ItemStack> pred, int count) {
        int ret = 0;
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if (item == null) continue;
            if (pred.test(item)) {
                if (item.getAmount() > count) {
                    item.setAmount(item.getAmount() - count);
                    ret += count;
                    break;

                } else if (item.getAmount() == count) {
                    ret += count;
                    inventory.setItem(i, null);
                    break;

                } else {
                    ret += item.getAmount();
                    count -= item.getAmount();
                    inventory.setItem(i, null);
                    // do not break
                }
            }

        }

        if (inventory instanceof PlayerInventory) ((Player)inventory.getHolder()).updateInventory();

        return ret;
    }

    public static int ClearItems(Inventory inventory, Material material, int count) {
        return ClearItems(inventory, c -> c.getType() == material, count);
    }

}
