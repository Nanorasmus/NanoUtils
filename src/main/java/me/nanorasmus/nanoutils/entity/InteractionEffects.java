package me.nanorasmus.nanoutils.entity;

import me.nanorasmus.nanoutils.Main;
import me.nanorasmus.nanoutils.data.file.ByteFileHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class InteractionEffects implements Listener {

    private static final String FILE_PATH = "interaction_effects";
    private static HashMap<UUID, HashMap<String, InteractionEffect>> effects = new HashMap<>();

    /**
     * DO NOT INSTANTIATE UNLESS YOU ARE THE MAIN CLASS OF NANOLIB ITSELF
     */
    public InteractionEffects(Main main) {
        main.getServer().getPluginManager().registerEvents(this, main);

        if (ByteFileHelper.Exists(FILE_PATH)) {
            effects = ByteFileHelper.Load(FILE_PATH, effects);
            main.getLogger().info("Loaded " + effects.size() + " interaction effects!");
        }
    }

    @EventHandler
    public void InteractionEvent(PlayerInteractEntityEvent e) {
        if (e.getHand() != EquipmentSlot.HAND) {
            return;
        }

        UUID entityUUID = e.getRightClicked().getUniqueId();
        if (effects.containsKey(entityUUID)) {
            for (InteractionEffect effect : effects.get(entityUUID).values()) {
                effect.run(e.getPlayer(), e.getRightClicked());
            }
        }
    }

    private static void PruneEffects() {
        ArrayList<UUID> toRemove = new ArrayList<>();
        for (UUID entityId : effects.keySet()) {
            if (Bukkit.getEntity(entityId) == null) {
                toRemove.add(entityId);
            }
        }

        for (UUID entityId : toRemove) {
            effects.remove(entityId);
        }
    }

    public static void SaveEffects() {
        if (!effects.isEmpty()) {
            PruneEffects();
            ByteFileHelper.Save(FILE_PATH, effects);
        }
    }

    public static boolean HasEffect(Entity entity) {
        return effects.get(entity.getUniqueId()) != null;
    }

    public static boolean HasEffect(Entity entity, String effectId) {
        if (!HasEffect(entity)) {
            return false;
        }

        return effects.get(entity.getUniqueId()).get(effectId) != null;
    }

    public static void AddEffect(Entity entity, String id, InteractionEffect runnable) {
        if (!HasEffect(entity)) {
            effects.put(entity.getUniqueId(), new HashMap<>());
        }

        effects.get(entity.getUniqueId()).put(id, runnable);
    }

    public static void RemoveEffects(Entity entity) {
        if (!HasEffect(entity)) {
            return;
        }

        effects.remove(entity.getUniqueId());
    }

    public static void RemoveEffect(Entity entity, String id) {
        if (!HasEffect(entity, id)) {
            return;
        }

        effects.get(entity.getUniqueId()).remove(id);
        if (effects.get(entity.getUniqueId()).isEmpty()) {
            effects.remove(entity.getUniqueId());
        }
    }

    public static ArrayList<String> GetEffects(Entity entity) {
        ArrayList<String> output = new ArrayList<>();

        if (!HasEffect(entity)) {
            return output;
        }

        output.addAll(effects.get(entity.getUniqueId()).keySet());
        return output;
    }
}
