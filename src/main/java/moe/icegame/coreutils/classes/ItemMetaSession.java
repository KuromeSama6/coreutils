package moe.icegame.coreutils.classes;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemMetaSession<T extends ItemMeta> implements AutoCloseable{
    public final T meta;
    public final ItemStack item;

    public ItemMetaSession(ItemStack item) {
        this.item = item;
        this.meta = (T)item.getItemMeta();
    }

    @Override
    public void close() {
        item.setItemMeta(meta);
    }
}
