package me.nanorasmus.nanoutils.data.nbt;

import me.nanorasmus.nanoutils.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;

public class ItemStackNBT {
    private static NamespacedKey makeKey(String suffix) {
        return new NamespacedKey(Main.plugin, "Nano-" + suffix);
    }

    /**
     * Stores a Boolean in an ItemStack
     * @param key the key to store the data under
     * @param value the Boolean to store
     * @param itemStack the ItemStack to store the data in
     * @return the ItemStack the data was stored in
     */
    public static ItemStack saveToItemStack(String key, Boolean value, ItemStack itemStack) {
        NamespacedKey namespacedKey = makeKey(key);

        ItemMeta meta = itemStack.getItemMeta();

        meta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.BOOLEAN, value);

        itemStack.setItemMeta(meta);

        return itemStack;
    }


    /**
     * Stores a String in an ItemStack
     * @param key the key to store the data under
     * @param value the String to store
     * @param itemStack the ItemStack to store the data in
     * @return the ItemStack the data was stored in
     */
    public static ItemStack saveToItemStack(String key, String value, ItemStack itemStack) {
        NamespacedKey namespacedKey = makeKey(key);

        ItemMeta meta = itemStack.getItemMeta();

        meta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.STRING, value);

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    /**
     * Stores a Double in an ItemStack
     * @param key the key to store the data under
     * @param value the Double to store
     * @param itemStack the ItemStack to store the data in
     * @return the ItemStack the data was stored in
     */
    public static ItemStack saveToItemStack(String key, Double value, ItemStack itemStack) {
        NamespacedKey namespacedKey = makeKey(key);

        ItemMeta meta = itemStack.getItemMeta();

        meta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.DOUBLE, value);

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    /**
     * Stores an Integer in an ItemStack
     * @param key the key to store the data under
     * @param value the Integer to store
     * @param itemStack the ItemStack to store the data in
     * @return the ItemStack the data was stored in
     */
    public static ItemStack saveToItemStack(String key, Integer value, ItemStack itemStack) {
        NamespacedKey namespacedKey = makeKey(key);

        ItemMeta meta = itemStack.getItemMeta();

        meta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.INTEGER, value);

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    /**
     * Stores an int array in an ItemStack
     * @param key the key to store the data under
     * @param value the int array to store
     * @param itemStack the ItemStack to store the data in
     * @return the ItemStack the data was stored in
     */
    public static ItemStack saveToItemStack(String key, int[] value, ItemStack itemStack) {
        NamespacedKey namespacedKey = makeKey(key);

        ItemMeta meta = itemStack.getItemMeta();

        meta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.INTEGER_ARRAY, value);

        itemStack.setItemMeta(meta);

        return itemStack;
    }



    /**
     * Retrieves data stored in an ItemStack as a Boolean
     * @param key the key the data was stored under
     * @param itemStack the ItemStack the data was stored in
     * @return the stored Boolean
     */
    @Nullable
    public static Boolean getBooleanFromItemStack(String key, ItemStack itemStack) {
        NamespacedKey namespacedKey = makeKey(key);

        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) return null;

        return meta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.BOOLEAN);
    }


    /**
     * Retrieves data stored in an ItemStack as a String
     * @param key the key the data was stored under
     * @param itemStack the ItemStack the data was stored in
     * @return the stored String
     */
    @Nullable
    public static String getStringFromItemStack(String key, ItemStack itemStack) {
        NamespacedKey namespacedKey = makeKey(key);

        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) return null;

        return meta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.STRING);
    }


    /**
     * Retrieves data stored in an ItemStack as a Double
     * @param key the key the data was stored under
     * @param itemStack the ItemStack the data was stored in
     * @return the stored Double
     */
    @Nullable
    public static Double getDoubleFromItemStack(String key, ItemStack itemStack) {
        NamespacedKey namespacedKey = makeKey(key);

        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) return null;

        return meta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.DOUBLE);
    }

    /**
     * Retrieves data stored in an ItemStack as an Integer
     * @param key the key the data was stored under
     * @param itemStack the ItemStack the data was stored in
     * @return the stored Integer
     */
    @Nullable
    public static Integer getIntFromItemStack(String key, ItemStack itemStack) {
        NamespacedKey namespacedKey = makeKey(key);

        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) return null;

        return meta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.INTEGER);
    }

    /**
     * Retrieves data stored in an ItemStack as an int array
     * @param key the key the data was stored under
     * @param itemStack the ItemStack the data was stored in
     * @return the stored int array
     */
    @Nullable
    public static int[] getIntArrayFromItemStack(String key, ItemStack itemStack) {
        NamespacedKey namespacedKey = makeKey(key);

        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) return null;

        return meta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.INTEGER_ARRAY);
    }

    /**
     * Checks if an ItemStack contains any data under the specified key
     * @param key the key to check for data
     * @param itemStack the ItemStack to check for data inside of
     * @return true if there is data, false if not.
     */
    public static boolean ItemStackHasDataUnderKey(String key, ItemStack itemStack) {
        NamespacedKey namespacedKey = makeKey(key);

        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) return false;

        return meta.getPersistentDataContainer().has(namespacedKey);
    }
}
