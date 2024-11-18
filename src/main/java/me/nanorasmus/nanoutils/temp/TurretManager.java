package me.nanorasmus.nanoutils.temp;

import me.nanorasmus.nanoutils.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class TurretManager implements Listener {
    public BukkitRunnable turretTicker;

    public ArrayList<BeeTurret> turrets = new ArrayList<>();

    public TurretManager(Main main) {
        main.getServer().getPluginManager().registerEvents(this, main);

        turretTicker = new BukkitRunnable() {
            @Override
            public void run() {
                for (BeeTurret turret : turrets) {
                    turret.tick();
                }
                turrets.removeIf((beeTurret -> beeTurret.isDead));
            }
        };

        turretTicker.runTaskTimer(main, 0, 1);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        // Save the player to variable p
        Player p = e.getPlayer();

        // Get the used item and make sure that it is not null
        ItemStack inHand = p.getInventory().getItemInMainHand();

        if (inHand.getType().equals(Material.HONEY_BOTTLE) && p.isSneaking()) {
            turrets.add(new BeeTurret(p.getTargetBlock(null, 20).getLocation().add(0.5, 3,0.5)));
        }
    }
}
