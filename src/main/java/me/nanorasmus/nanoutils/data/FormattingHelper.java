package me.nanorasmus.nanoutils.data;

import org.bukkit.Location;

public class FormattingHelper {
    /**
     * Converts a location to a location-specific string, useful for location to object hashmaps.
     */
    public static String LocationToString(Location loc) {
        return loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ() + ", " + loc.getWorld().getName();
    }
}
