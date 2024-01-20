package me.nanorasmus.nanoutils.structures;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;

import java.util.ArrayList;

public class Structure {
    public final ArrayList<SavedBlock> blocks = new ArrayList();

    public Structure() {

    }

    public Structure(Location anchor, Location loc1, Location loc2, boolean saveAir) {
        int x1 = loc1.getBlockX();
        int y1 = loc1.getBlockY();
        int z1 = loc1.getBlockZ();
        int x2 = loc2.getBlockX();
        int y2 = loc2.getBlockY();
        int z2 = loc2.getBlockZ();
        int buffer;
        if (x1 > x2) {
            buffer = x1;
            x1 = x2;
            x2 = buffer;
        }

        if (y1 > y2) {
            buffer = y1;
            y1 = y2;
            y2 = buffer;
        }

        if (z1 > z2) {
            buffer = z1;
            z1 = z2;
            z2 = buffer;
        }

        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                for (int z = z1; z <= z2; z++) {
                    Block block = anchor.getWorld().getBlockAt(x, y, z);
                    if (!saveAir && block.getType() == Material.AIR) {
                        continue;
                    }
                    BlockFace rotation = null;

                    if (block.getBlockData() instanceof Directional) {
                        Directional directional = (Directional) block.getBlockData();
                        rotation = directional.getFacing();
                    } else if (block.getBlockData() instanceof Rotatable) {
                        Rotatable rotatable = (Rotatable) block.getBlockData();
                        rotation = rotatable.getRotation();
                    }
                    blocks.add(new SavedBlock(x - anchor.getBlockX(), y - anchor.getBlockY(), z - anchor.getBlockZ(), block.getType(), rotation));
                }
            }
        }
    }

    public void Paste(Location anchor, boolean onlyReplaceAir) {
        blocks.forEach((savedBlock) -> {
            // Get coordinates
            int x = savedBlock.x;
            int y = savedBlock.y;
            int z = savedBlock.z;

            World world = anchor.getWorld();
            Block block = world.getBlockAt(anchor.getBlockX() + x, anchor.getBlockY() + y, anchor.getBlockZ() + z);

            if (onlyReplaceAir && world.getBlockAt(anchor.getBlockX() + x, anchor.getBlockY() + y, anchor.getBlockZ() + z).getType() != Material.AIR) return;

            savedBlock.Place(anchor);
        });
    }

    // Checks if the structure fits the criteria of other
    public boolean containsAll(Structure other) {

        // For each criteria
        for (SavedBlock criteria : other.blocks) {

            // Find a matching block
            boolean criteriaMet = false;
            for (SavedBlock block : blocks) {
                if (
                        block.x == criteria.x
                        && block.y == criteria.y
                        && block.z == criteria.z
                        && block.material == criteria.material
                        && block.rotation == criteria.rotation
                ) {
                    criteriaMet = true;
                    break;
                }
            };

            // Return false if no matching block was found
            if (!criteriaMet) {
                return false;
            }
        };
        return true;
    }

    /// Compares 2 structures and returns whether they are equal or not
    public static boolean Equals(Structure a, Structure b) {
        return a.containsAll(b) && b.containsAll(a);
    }
}
