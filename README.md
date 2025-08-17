# Overview
NanoUtils is a library plugin for minecraft used in all Nano plugins.
However, anyone is free to use it as-is in any of their projects.

NanoUtils provides methods such as:
- Manage customizable multiblock structures (NBT not included)
- Saving a class to a file
- Loading a class from a file w/ a backup instance in case of an error
- Saving primitive variables to ItemStacks
- Loading primitive variables from Itemstacks
- And more!

# Tutorials
## Creating a multiblock structure
First, build the structure in game, then write some code that does the following things:
- Set one of 2 variables containing the location of a target block, for use as corners of a bounding box
- Copy a structure using the bounding box from before, and the current target block as the origin, and then save it to a file.
Here's an example of this code using a stick as the bounding box selector, and a named piece of paper as the save item:

```Java
import me.nanorasmus.nanoutils.data.file.JSONFileHelper;
import me.nanorasmus.nanoutils.structures.Structure;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MultiblockMaker implements Listener {

    Location pos1;
    Location pos2;

    public MultiblockMaker(Main main) {
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    void onInteract(PlayerInteractEvent e) {
        // Save the player to variable p
        Player p = e.getPlayer();

        // Get the used item and make sure that it is not null
        ItemStack inHand = p.getItemInUse();
        if (inHand == null)
            return;

        // If the player is trying to save a position
        if (inHand.getType() == Material.STICK) {
            // Save target block to pos1 if they are not sneaking
            if (!p.isSneaking()) {
                pos1 = p.getTargetBlock(null, 50).getLocation();
                p.sendMessage("Saved target block to position 1!");
            }
            // Else save it to pos2
            else {
                pos2 = p.getTargetBlock(null, 50).getLocation();
                p.sendMessage("Saved target block to position 2!");
            }
        }
        // If the player is trying to save the structure and both positions are set
        else if (inHand.getType() == Material.PAPER && pos1 != null && pos2 != null) {
            // Get the name of the paper and make sure that it is not null
            ItemMeta paper = inHand.getItemMeta();
            if (paper == null)
                return;

            // Make a new structure with the target block as the origin and the 2 positions as a bounding box
            // Note that we are setting SaveAir to false so that non-cuboid shapes can work properly
            Location origin = p.getTargetBlock(null, 50).getLocation();
            Structure structure = new Structure(origin, pos1, pos2, false);

            // Save the structure
            JSONFileHelper.Save("Saves/" + paper.getDisplayName() + ".json", structure);
        }
    }
}
```
And then in the main class, simply instanciate MultiblockMaker and save it to a variable:
```Java
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    MultiblockMaker multiblockMaker;

    @Override
    public void onEnable() {
        multiblockMaker = new MultiblockMaker(this);
    }
}
```
