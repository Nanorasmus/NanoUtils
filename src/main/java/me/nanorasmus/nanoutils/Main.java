package me.nanorasmus.nanoutils;

import me.nanorasmus.nanoutils.temp.TPScroll;
import me.nanorasmus.nanoutils.temp.TurretManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class Main extends JavaPlugin {

    public static Main main;
    public static JavaPlugin plugin;

    public TPScroll tpScroll;
    public TurretManager turretManager;


    @Override
    public void onEnable() {
        getLogger().info("Setting up NanoUtils...");

        // Set static references to this class
        main = this;
        plugin = this;

        tpScroll = new TPScroll(this);
        turretManager = new TurretManager(this);

        // Initialize Utils
        FileUtils.Init();

        getLogger().info("NanoUtils is set up!");
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
