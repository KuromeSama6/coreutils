package moe.icegame.coreutils.classes;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import moe.icegame.coreutils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.PotionMeta;
import org.yaml.snakeyaml.Yaml;

import java.util.*;

public class InventoryRepresenter {
    private final int SIZE = 41;
    @Getter private final List<ItemStack> content = new ArrayList<>();
    public boolean isReady;

    public InventoryRepresenter() {
        for (int i = 0; i < SIZE; i++) content.add(null);
    }

    public InventoryRepresenter(ConfigurationSection cfg) {
        // inventory
        for (String key : cfg.getKeys(false)) {
            if (!cfg.isItemStack(key)) {
                content.add(null);
                continue;
            }
            content.add(cfg.getItemStack(key));
        }

        isReady = true;
    }

    public InventoryRepresenter(PlayerInventory inv) {
        // inventory
        content.addAll(Arrays.asList(inv.getContents()));

        isReady = true;
    }

    public void Reset() {
        content.clear();
        for (int i = 0; i < SIZE; i++) content.add(null);
    }

    public boolean IsEmpty(){
        return content.size() == 0;
    }

    public void ReadFrom(PlayerInventory inv) {
        content.clear();
        content.addAll(Arrays.asList(inv.getContents()));
    }

    public void WriteTo(PlayerInventory inv) {
        inv.setContents(content.toArray(new ItemStack[0]));
    }

    public ConfigurationSection Dump(){
        YamlConfiguration ret = new YamlConfiguration();

        int index = 0;
        for (ItemStack item : content) {
            ret.set(String.format("%s", index), item == null || item.getType() == Material.AIR ? "null" : item);
            index++;
        }

        return ret;
    }

    public JsonObject DumpJson(){
        JsonObject ret = new JsonObject();

        JsonArray inventoryItems = new JsonArray();
        int index = 0;
        for (ItemStack item : content) {
            inventoryItems.add(SerializeItem(item));
            index++;
        }

        ret.add("inventory", inventoryItems);
        ret.add("armor", inventoryItems);

        return ret;
    }

    public boolean ContainsItem(ItemStack item) {
        return content.stream().anyMatch(c -> ItemUtil.CompareItems(c, item));
    }

    public static InventoryRepresenter FromString(String cfg) {
        YamlConfiguration configuration = new YamlConfiguration();
        try {
            configuration.loadFromString(cfg);
            return new InventoryRepresenter(configuration);
        } catch (InvalidConfigurationException | NullPointerException e) {
            e.printStackTrace();
        }
        return new InventoryRepresenter();
    }

    public static JsonElement SerializeItem(ItemStack item) {
        if (item == null) return null;

        return new Gson().toJsonTree(item, ItemStack.class);
    }

}
