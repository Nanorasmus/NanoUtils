package me.nanorasmus.nanoutils.temp;

import me.nanorasmus.nanoutils.Main;
import me.nanorasmus.nanoutils.NBTUtils;
import me.nanorasmus.nanoutils.NumberUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class TPScroll implements Listener {
    public TPScroll(Main main) {
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        // Save the player to variable p
        Player p = e.getPlayer();

        // Get the used item and make sure that it is not null
        ItemStack inHand = p.getInventory().getItemInMainHand();

        if (inHand.getType().equals(Material.PAPER)) {
            ItemMeta meta = inHand.getItemMeta();
            String[] nameChunks = meta.getDisplayName().split(" ");
            if (
                    nameChunks.length < 4 || !nameChunks[0].equalsIgnoreCase("tp")
                            || !NumberUtils.isNumeric(nameChunks[1])
                            || !NumberUtils.isNumeric(nameChunks[2])
                            || !NumberUtils.isNumeric(nameChunks[3])
            ) {
                return;
            }

            // Create TP Scroll
            inHand.setType(Material.FLOWER_BANNER_PATTERN);

            NBTUtils.saveToItemStack("tp-dest-x", Double.parseDouble(nameChunks[1]), inHand);
            NBTUtils.saveToItemStack("tp-dest-y", Double.parseDouble(nameChunks[2]), inHand);
            NBTUtils.saveToItemStack("tp-dest-z", Double.parseDouble(nameChunks[3]), inHand);

            ItemMeta scrollMeta = inHand.getItemMeta();
            scrollMeta.setDisplayName("§bTeleport scroll (" + nameChunks[1] + ", " + nameChunks[2] + ", " + nameChunks[3] + ")");
            scrollMeta.setLore(Arrays.asList("Sneak and right-click to use!"));
            inHand.setItemMeta(scrollMeta);
            return;
        }
        if (inHand.getType().equals(Material.FLOWER_BANNER_PATTERN)) {
            if (!p.isSneaking()) {
                return;
            }

            Double x = NBTUtils.getDoubleFromItemStack("tp-dest-x", inHand);
            Double y = NBTUtils.getDoubleFromItemStack("tp-dest-y", inHand);
            Double z = NBTUtils.getDoubleFromItemStack("tp-dest-z", inHand);

            if (x == null || y == null || z == null) {
                return;
            }

            p.teleport(new Location(p.getWorld(), x, y, z));
        }
    }
}
