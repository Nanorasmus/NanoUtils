package me.nanorasmus.nanoutils.structures;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;

public class SavedBlock {
    public int x;
    public int y;
    public int z;

    public Material material;
    public BlockFace rotation;

    public SavedBlock( int x,  int y,  int z,  Material material, BlockFace rotation) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.material = material;
        this.rotation = rotation;
    }

    public void Place( Location anchor) {
        World world = anchor.getWorld();
        Block block = world.getBlockAt(anchor.getBlockX() + this.x, anchor.getBlockY() + this.y, anchor.getBlockZ() + this.z);
        block.setType(this.material);
        if (block.getBlockData() instanceof Directional) {
            Directional directional = (Directional) block.getBlockData();
            directional.setFacing(this.rotation);
            block.setBlockData(directional);
            block.getState().update();
        } else if (block.getBlockData() instanceof Rotatable) {
            Rotatable rotatable = (Rotatable) block.getBlockData();
            rotatable.setRotation(this.rotation);
            block.setBlockData(rotatable);
            block.getState().update();
        }
    }
}
