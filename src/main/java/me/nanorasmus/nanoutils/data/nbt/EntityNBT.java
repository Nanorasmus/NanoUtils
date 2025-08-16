package me.nanorasmus.nanoutils.data.nbt;

import me.nanorasmus.nanoutils.Main;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class EntityNBT {
    /**
     * Saves data to an entity,
     * Does NOT persist on server restart
     * @param key The key to store the data under
     * @param value The value to store
     * @param entity The entity to store the data to
     * @return The entity the data was stored in
     */
    public static Entity saveToEntity(String key, Object value, Entity entity) {
        entity.setMetadata("Nano-" + key, new FixedMetadataValue(Main.plugin, value));
        return entity;
    }

    /**
     * Retrieves data stored in an entity
     * @param key the key the data was stored under
     * @param entity the ItemStack the data was stored in
     * @return the stored data
     */
    public static Object getFromEntity(String key, Entity entity) {
        List<MetadataValue> value = entity.getMetadata("Nano-" + key);

        if (value.isEmpty()) return null;

        return value.get(0).value();
    }
}
