package me.nanorasmus.nanoutils;

import me.nanorasmus.nanoutils.data.file.ByteFileHelper;
import me.nanorasmus.nanoutils.data.file.JSONFileHelper;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main main;
    public static JavaPlugin plugin;


    @Override
    public void onEnable() {
        getLogger().info("Setting up NanoUtils...");

        // Set static references to this class
        main = this;
        plugin = this;

        // Initialize Utils
        ByteFileHelper.Init();
        JSONFileHelper.Init();


        getLogger().info("NanoUtils is set up!");
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
