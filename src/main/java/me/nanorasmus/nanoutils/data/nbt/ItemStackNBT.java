package me.nanorasmus.nanoutils.data.nbt;

import me.nanorasmus.nanoutils.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;

public class ItemStackNBT {
    private static <P, C> ItemStack saveToItemStack(NamespacedKey key, C value, ItemStack itemStack, PersistentDataType<P, C> dataType) {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) {
            meta = Main.plugin.getServer().getItemFactory().getItemMeta(itemStack.getType());
            if (meta == null) {
                Main.plugin.getLogger().severe("Tried saving data to invalid ItemStack under key \"" + key + "\"");
                return itemStack;
            }
        }

        meta.getPersistentDataContainer().set(key, dataType, value);

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    private static <P, C> C getFromItemStack(NamespacedKey key, ItemStack itemStack, PersistentDataType<P, C> dataType) {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) return null;

        return meta.getPersistentDataContainer().get(key, dataType);
    }

    /**
     * Stores a Boolean in an ItemStack
     * @param key the key to store the data under
     * @param value the Boolean to store
     * @param itemStack the ItemStack to store the data in
     * @return the ItemStack the data was stored in
     */
    public static ItemStack saveToItemStack(NamespacedKey key, Boolean value, ItemStack itemStack) {
        return saveToItemStack(key, value, itemStack, PersistentDataType.BOOLEAN);
    }


    /**
     * Stores a String in an ItemStack
     * @param key the key to store the data under
     * @param value the String to store
     * @param itemStack the ItemStack to store the data in
     * @return the ItemStack the data was stored in
     */
    public static ItemStack saveToItemStack(NamespacedKey key, String value, ItemStack itemStack) {
        return saveToItemStack(key, value, itemStack, PersistentDataType.STRING);
    }

    /**
     * Stores a Double in an ItemStack
     * @param key the key to store the data under
     * @param value the Double to store
     * @param itemStack the ItemStack to store the data in
     * @return the ItemStack the data was stored in
     */
    public static ItemStack saveToItemStack(NamespacedKey key, Double value, ItemStack itemStack) {
        return saveToItemStack(key, value, itemStack, PersistentDataType.DOUBLE);
    }

    /**
     * Stores an Integer in an ItemStack
     * @param key the key to store the data under
     * @param value the Integer to store
     * @param itemStack the ItemStack to store the data in
     * @return the ItemStack the data was stored in
     */
    public static ItemStack saveToItemStack(NamespacedKey key, Integer value, ItemStack itemStack) {
        return saveToItemStack(key, value, itemStack, PersistentDataType.INTEGER);
    }

    /**
     * Stores an int array in an ItemStack
     * @param key the key to store the data under
     * @param value the int array to store
     * @param itemStack the ItemStack to store the data in
     * @return the ItemStack the data was stored in
     */
    public static ItemStack saveToItemStack(NamespacedKey key, int[] value, ItemStack itemStack) {
        return saveToItemStack(key, value, itemStack, PersistentDataType.INTEGER_ARRAY);
    }

    /**
     * Stores a byte array in an ItemStack
     * @param key the key to store the data under
     * @param value the byte array to store
     * @param itemStack the ItemStack to store the data in
     * @return the ItemStack the data was stored in
     */
    public static ItemStack saveToItemStack(NamespacedKey key, byte[] value, ItemStack itemStack) {
        return saveToItemStack(key, value, itemStack, PersistentDataType.BYTE_ARRAY);
    }



    /**
     * Retrieves data stored in an ItemStack as a Boolean
     * @param key the key the data was stored under
     * @param itemStack the ItemStack the data was stored in
     * @return the stored Boolean
     */
    @Nullable
    public static Boolean getBooleanFromItemStack(NamespacedKey key, ItemStack itemStack) {
        return getFromItemStack(key, itemStack, PersistentDataType.BOOLEAN);
    }


    /**
     * Retrieves data stored in an ItemStack as a String
     * @param key the key the data was stored under
     * @param itemStack the ItemStack the data was stored in
     * @return the stored String
     */
    @Nullable
    public static String getStringFromItemStack(NamespacedKey key, ItemStack itemStack) {
        return getFromItemStack(key, itemStack, PersistentDataType.STRING);
    }


    /**
     * Retrieves data stored in an ItemStack as a Double
     * @param key the key the data was stored under
     * @param itemStack the ItemStack the data was stored in
     * @return the stored Double
     */
    @Nullable
    public static Double getDoubleFromItemStack(NamespacedKey key, ItemStack itemStack) {
        return getFromItemStack(key, itemStack, PersistentDataType.DOUBLE);
    }

    /**
     * Retrieves data stored in an ItemStack as an Integer
     * @param key the key the data was stored under
     * @param itemStack the ItemStack the data was stored in
     * @return the stored Integer
     */
    @Nullable
    public static Integer getIntFromItemStack(NamespacedKey key, ItemStack itemStack) {
        return getFromItemStack(key, itemStack, PersistentDataType.INTEGER);
    }

    /**
     * Retrieves data stored in an ItemStack as an int array
     * @param key the key the data was stored under
     * @param itemStack the ItemStack the data was stored in
     * @return the stored int array
     */
    @Nullable
    public static int[] getIntArrayFromItemStack(NamespacedKey key, ItemStack itemStack) {
        return getFromItemStack(key, itemStack, PersistentDataType.INTEGER_ARRAY);
    }

    /**
     * Retrieves data stored in an ItemStack as a byte array
     * @param key the key the data was stored under
     * @param itemStack the ItemStack the data was stored in
     * @return the stored byte array
     */
    @Nullable
    public static byte[] getByteArrayFromItemStack(NamespacedKey key, ItemStack itemStack) {
        return getFromItemStack(key, itemStack, PersistentDataType.BYTE_ARRAY);
    }

    /**
     * Checks if an ItemStack contains any data under the specified key
     * @param key the key to check for data
     * @param itemStack the ItemStack to check for data inside of
     * @return true if there is data, false if not.
     */
    public static boolean ItemStackHasDataUnderKey(NamespacedKey key, ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) return false;

        return meta.getPersistentDataContainer().has(key);
    }
}
