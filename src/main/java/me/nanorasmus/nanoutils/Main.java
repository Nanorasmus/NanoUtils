package me.nanorasmus.nanoutils;

import com.esotericsoftware.kryo.kryo5.Kryo;
import com.esotericsoftware.kryo.kryo5.io.Input;
import com.esotericsoftware.kryo.kryo5.io.Output;
import com.esotericsoftware.kryo.kryo5.serializers.DefaultSerializers;
import me.nanorasmus.nanoutils.data.SerializationHelper;
import me.nanorasmus.nanoutils.data.file.ByteFileHelper;
import me.nanorasmus.nanoutils.data.file.JSONFileHelper;
import me.nanorasmus.nanoutils.entity.InteractionEffects;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public final class Main extends JavaPlugin {

    public static Main main;
    public static JavaPlugin plugin;

    InteractionEffects interactionEffects;

    @Override
    public void onEnable() {
        getLogger().info("Setting up NanoUtils...");

        // Set static references to this class
        main = this;
        plugin = this;

        // Initialize serialization helper
        SerializationHelper.Init();

        // Initialize File helpers
        ByteFileHelper.Init();
        JSONFileHelper.Init();


        // Interaction helpers
        interactionEffects = new InteractionEffects(this);

        getLogger().info("NanoUtils is set up!");
    }


    @Override
    public void onDisable() {
        InteractionEffects.SaveEffects();
    }
}
