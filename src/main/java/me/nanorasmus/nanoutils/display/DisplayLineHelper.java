package me.nanorasmus.nanoutils.display;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Transformation;
import org.joml.*;

import java.lang.Math;

public class DisplayLineHelper {
    public static BlockDisplay createLine(World world, Vector3d a, Vector3d b, Material block, float thickness, float padding) {
        // Initialize line
        BlockDisplay line = (BlockDisplay) world.spawnEntity(new Location(world, a.x, a.y, a.z), EntityType.BLOCK_DISPLAY);
        line.setBlock(Bukkit.createBlockData(block));

        // Translate the line and return
        return translateLine(line, a, b, thickness, padding);
    }

    public static BlockDisplay translateLine(BlockDisplay line, Vector3d a, Vector3d b, float thickness, float padding) {

        // Get new position
        Vector3d pos = new Vector3d(a).add(b).div(2);

        // Get pitch and yaw
        Vector3f offsetNormalized = new Vector3f((float) b.x, (float) b.y, (float) b.z).sub((float) a.x, (float) a.y, (float) a.z).normalize();

        float pitch = (float) Math.toDegrees(Math.asin(-offsetNormalized.y));
        float yaw = (float) Math.toDegrees(Math.atan2(offsetNormalized.x, offsetNormalized.z));
        // Move the line
        line.teleport(new Location(
                line.getWorld(),
                pos.x,
                pos.y,
                pos.z,
                -yaw, pitch + 90
        ));

        Vector3f side = new Vector3f(offsetNormalized).cross(new Vector3f(0, 1, 0)).normalize();
        Vector3f up = new Vector3f(side).cross(offsetNormalized).normalize();
        Matrix3f rotationMatrix = new Matrix3f(
                offsetNormalized.x, offsetNormalized.y, offsetNormalized.z,
                up.x, up.y, up.z,
                side.x, side.y, side.z
        );
//        Matrix3f rotationMatrix = new Matrix3f(
//                offsetNormalized.x, up.x, side.x,
//                offsetNormalized.y, up.y, side.y,
//                offsetNormalized.z, up.z, side.z
//        );

        Quaternionf rotation = new Quaternionf().setFromUnnormalized(rotationMatrix);
        float length = (float) a.distance(b) + padding * 2;
        Transformation transform = new Transformation(
                new Vector3f(-thickness / 2, -length / 2, -thickness / 2),
                new Quaternionf(),
                new Vector3f(thickness, length, thickness),
                new Quaternionf()
        );

        line.setTransformation(transform);

        // Return the line for chaining purposes
        return line;
    }
}
