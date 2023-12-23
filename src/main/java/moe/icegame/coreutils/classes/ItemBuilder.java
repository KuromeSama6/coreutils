package moe.icegame.coreutils.classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ItemBuilder {
    protected ItemStack item;

    public ItemBuilder(Material material, int qty) {
        item = new ItemStack(material, qty);
    }

    public ItemBuilder disp(String str) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', str));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder lore(String... lores) {
        ItemMeta meta = item.getItemMeta();
        List<String> current = meta.getLore();
        if (current == null) current = new ArrayList<>();
        current.addAll(Arrays.stream(lores).map(c -> ChatColor.translateAlternateColorCodes('&', c)).collect(Collectors.toList()));
        meta.setLore(current);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder aux(int aux) {
        MaterialData data = item.getData();
        data.setData((byte) aux);
        item.setData(data);
        return this;
    }

    public ItemBuilder enchant(Enchantment ench, int level) {
        item.addUnsafeEnchantment(ench, level);
        return this;
    }

    public ItemBuilder func(Consumer<ItemStack> func) {
        func.accept(item);
        return this;
    }

    public ItemBuilder replace(ItemStack item) {
        this.item = item;
        return this;
    }

    public ItemBuilder replace(Supplier<ItemStack> func) {
        this.item = func.get();
        return this;
    }

    public ItemStack build() {
        return item;
    }
}