package me.nanorasmus.nanoutils;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;
import java.util.List;

public class NBTUtils {
    public static ItemStack saveToItemStack(String key, String value, ItemStack itemStack) {
        NamespacedKey namespacedKey = new NamespacedKey(Main.plugin, "Nano-" + key);

        ItemMeta meta = itemStack.getItemMeta();

        meta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.STRING, value);

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public static ItemStack saveToItemStack(String key, Double value, ItemStack itemStack) {
        NamespacedKey namespacedKey = new NamespacedKey(Main.plugin, "Nano-" + key);

        ItemMeta meta = itemStack.getItemMeta();

        meta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.DOUBLE, value);

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public static ItemStack saveToItemStack(String key, int value, ItemStack itemStack) {
        NamespacedKey namespacedKey = new NamespacedKey(Main.plugin, "Nano-" + key);

        ItemMeta meta = itemStack.getItemMeta();

        meta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.INTEGER, value);

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public static ItemStack saveToItemStack(String key, int[] value, ItemStack itemStack) {
        NamespacedKey namespacedKey = new NamespacedKey(Main.plugin, "Nano-" + key);

        ItemMeta meta = itemStack.getItemMeta();

        meta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.INTEGER_ARRAY, value);

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    /**
     * Saves data to an entity,
     * Does NOT persist on server restart
     * @param key The key to store the data under
     * @param value The value to store
     * @param entity The entity to store the data to
     * @return The entity
     */
    public static Entity saveToEntity(String key, Object value, Entity entity) {
        entity.setMetadata("Nano-" + key, new FixedMetadataValue(Main.plugin, value));
        return entity;
    }

    @Nullable
    public static String getStringFromItemStack(String key, ItemStack itemStack) {
        NamespacedKey namespacedKey = new NamespacedKey(Main.plugin, "Nano-" + key);

        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) return null;

        return meta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.STRING);
    }

    @Nullable
    public static Double getDoubleFromItemStack(String key, ItemStack itemStack) {
        NamespacedKey namespacedKey = new NamespacedKey(Main.plugin, "Nano-" + key);

        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) return null;

        return meta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.DOUBLE);
    }

    @Nullable
    public static Integer getIntFromItemStack(String key, ItemStack itemStack) {
        NamespacedKey namespacedKey = new NamespacedKey(Main.plugin, "Nano-" + key);

        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) return null;

        return meta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.INTEGER);
    }

    @Nullable
    public static int[] getIntArrayFromItemStack(String key, ItemStack itemStack) {
        NamespacedKey namespacedKey = new NamespacedKey(Main.plugin, "Nano-" + key);

        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) return null;

        return meta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.INTEGER_ARRAY);
    }


    public static Object getFromEntity(String key, Entity entity) {
        List<MetadataValue> value = entity.getMetadata("Nano-" + key);

        if (value.isEmpty()) return "null";

        return value.get(0).value();
    }
}
