package moe.icegame.coreutils.nms;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NMSItemSession implements AutoCloseable{
    public final ItemStack item;
    public final net.minecraft.server.v1_12_R1.ItemStack nmsItem;

    public final NBTTagCompound nbt;

    public NMSItemSession(ItemStack item) {
        this.item = item;
        nmsItem = CraftItemStack.asNMSCopy(item);

        nbt = nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound();
    }

    @Override
    public void close() {
        nmsItem.setTag(nbt);
    }
}
