package moe.icegame.coreutils;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ItemUtil {
    public static boolean CompareItems(ItemStack a, ItemStack b) {
        try {
            if (a == null && b == null) return true;
            if (a == null || b == null) return false;
            if (a.equals(b)) return true;

            if (Objects.equals(a.getItemMeta().getDisplayName(), b.getItemMeta().getDisplayName())
                    && a.getType() == b.getType()
                    && (a.getItemMeta().getLore() == null && b.getItemMeta().getLore() == null
                    || String.join("", a.getItemMeta().getLore()).equals(String.join("", b.getItemMeta().getLore())))) return true;
        } catch (NullPointerException e) {
            return false;
        }

        return false;
    }

}
