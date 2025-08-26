package me.nanorasmus.nanoutils;

import me.nanorasmus.nanoutils.book.*;
import me.nanorasmus.nanoutils.data.SerializationHelper;
import me.nanorasmus.nanoutils.data.file.ByteFileHelper;
import me.nanorasmus.nanoutils.data.file.JSONFileHelper;
import me.nanorasmus.nanoutils.entity.DamageEffects;
import me.nanorasmus.nanoutils.entity.InteractionEffects;
import org.bukkit.plugin.java.JavaPlugin;


public final class Main extends JavaPlugin {

    public static Main main;
    public static JavaPlugin plugin;

    InteractionEffects interactionEffects;
    DamageEffects damageEffects;

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
        damageEffects = new DamageEffects(this);

        // Book system
        BookManager.Init();

        getLogger().info("NanoUtils is set up!");
    }


    @Override
    public void onDisable() {
        InteractionEffects.SaveEffects();
    }
}
