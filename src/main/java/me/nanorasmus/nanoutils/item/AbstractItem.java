package me.nanorasmus.nanoutils.item;

import me.nanorasmus.nanoutils.Main;
import me.nanorasmus.nanoutils.data.nbt.ItemStackNBT;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;

public abstract class AbstractItem implements Listener {
    private static final NamespacedKey itemIdKey = new NamespacedKey(Main.plugin, "item_id");

    public final NamespacedKey itemId;
    public final Material material;

    public AbstractItem(NamespacedKey itemId, Material material) {
        this.itemId = itemId;
        this.material = material;

        Main.plugin.getServer().getPluginManager().registerEvents(this, Main.plugin);
    }

    public boolean isApplicable(ItemStack item) {
        if (item == null || item.getType() != material || !item.hasItemMeta()) return false;

        String itemIdTag = ItemStackNBT.getStringFromItemStack(itemIdKey, item);

        return itemIdTag != null && itemIdTag.equalsIgnoreCase(itemId.toString());
    }

    public ItemStack makeItem(@Nullable Player player) {
        ItemStack item = new ItemStack(material);
        ItemStackNBT.saveToItemStack(itemIdKey, itemId.toString(), item);

        return finishItemCreation(item, player);
    }

    public ItemStack makeItem() {
        return makeItem(null);
    }

    public abstract ItemStack finishItemCreation(ItemStack itemStack, @Nullable Player player);
}
