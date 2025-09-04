package me.nanorasmus.nanoutils.data.nbt;

import me.nanorasmus.nanoutils.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;

public class EntityNBT {
    private static <P, C> Entity saveToEntity(NamespacedKey key, C value, Entity entity, PersistentDataType<P, C> dataType) {

        entity.getPersistentDataContainer().set(key, dataType, value);

        return entity;
    }

    private static <P, C> C getFromEntity(NamespacedKey key, Entity entity, PersistentDataType<P, C> dataType) {
        return entity.getPersistentDataContainer().get(key, dataType);
    }

    /**
     * Stores a Boolean in an Entity
     * @param key the key to store the data under
     * @param value the Boolean to store
     * @param entity the Entity to store the data in
     * @return the Entity the data was stored in
     */
    public static Entity saveToEntity(NamespacedKey key, Boolean value, Entity entity) {
        return saveToEntity(key, value, entity, PersistentDataType.BOOLEAN);
    }


    /**
     * Stores a String in an Entity
     * @param key the key to store the data under
     * @param value the String to store
     * @param entity the Entity to store the data in
     * @return the Entity the data was stored in
     */
    public static Entity saveToEntity(NamespacedKey key, String value, Entity entity) {
        return saveToEntity(key, value, entity, PersistentDataType.STRING);
    }

    /**
     * Stores a Double in an Entity
     * @param key the key to store the data under
     * @param value the Double to store
     * @param entity the Entity to store the data in
     * @return the Entity the data was stored in
     */
    public static Entity saveToEntity(NamespacedKey key, Double value, Entity entity) {
        return saveToEntity(key, value, entity, PersistentDataType.DOUBLE);
    }

    /**
     * Stores an Integer in an Entity
     * @param key the key to store the data under
     * @param value the Integer to store
     * @param entity the Entity to store the data in
     * @return the Entity the data was stored in
     */
    public static Entity saveToEntity(NamespacedKey key, Integer value, Entity entity) {
        return saveToEntity(key, value, entity, PersistentDataType.INTEGER);
    }

    /**
     * Stores an int array in an Entity
     * @param key the key to store the data under
     * @param value the int array to store
     * @param entity the Entity to store the data in
     * @return the Entity the data was stored in
     */
    public static Entity saveToEntity(NamespacedKey key, int[] value, Entity entity) {
        return saveToEntity(key, value, entity, PersistentDataType.INTEGER_ARRAY);
    }

    /**
     * Stores a byte array in an Entity
     * @param key the key to store the data under
     * @param value the byte array to store
     * @param entity the Entity to store the data in
     * @return the Entity the data was stored in
     */
    public static Entity saveToEntity(NamespacedKey key, byte[] value, Entity entity) {
        return saveToEntity(key, value, entity, PersistentDataType.BYTE_ARRAY);
    }



    /**
     * Retrieves data stored in an Entity as a Boolean
     * @param key the key the data was stored under
     * @param entity the Entity the data was stored in
     * @return the stored Boolean
     */
    @Nullable
    public static Boolean getBooleanFromEntity(NamespacedKey key, Entity entity) {
        return getFromEntity(key, entity, PersistentDataType.BOOLEAN);
    }


    /**
     * Retrieves data stored in an Entity as a String
     * @param key the key the data was stored under
     * @param entity the Entity the data was stored in
     * @return the stored String
     */
    @Nullable
    public static String getStringFromEntity(NamespacedKey key, Entity entity) {
        return getFromEntity(key, entity, PersistentDataType.STRING);
    }


    /**
     * Retrieves data stored in an Entity as a Double
     * @param key the key the data was stored under
     * @param entity the Entity the data was stored in
     * @return the stored Double
     */
    @Nullable
    public static Double getDoubleFromEntity(NamespacedKey key, Entity entity) {
        return getFromEntity(key, entity, PersistentDataType.DOUBLE);
    }

    /**
     * Retrieves data stored in an Entity as an Integer
     * @param key the key the data was stored under
     * @param entity the Entity the data was stored in
     * @return the stored Integer
     */
    @Nullable
    public static Integer getIntFromEntity(NamespacedKey key, Entity entity) {
        return getFromEntity(key, entity, PersistentDataType.INTEGER);
    }

    /**
     * Retrieves data stored in an Entity as an int array
     * @param key the key the data was stored under
     * @param entity the Entity the data was stored in
     * @return the stored int array
     */
    @Nullable
    public static int[] getIntArrayFromEntity(NamespacedKey key, Entity entity) {
        return getFromEntity(key, entity, PersistentDataType.INTEGER_ARRAY);
    }

    /**
     * Retrieves data stored in an Entity as a byte array
     * @param key the key the data was stored under
     * @param entity the Entity the data was stored in
     * @return the stored byte array
     */
    @Nullable
    public static byte[] getByteArrayFromEntity(NamespacedKey key, Entity entity) {
        return getFromEntity(key, entity, PersistentDataType.BYTE_ARRAY);
    }

    /**
     * Checks if an Entity contains any data under the specified key
     * @param key the key to check for data
     * @param entity the Entity to check for data inside of
     * @return true if there is data, false if not.
     */
    public static boolean EntityHasDataUnderKey(NamespacedKey key, Entity entity) {
        return entity.getPersistentDataContainer().has(key);
    }
}
