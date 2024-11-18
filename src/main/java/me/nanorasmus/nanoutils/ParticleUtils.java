package me.nanorasmus.nanoutils;

import org.bukkit.Particle;
import org.bukkit.World;
import org.joml.Vector3d;

import javax.annotation.Nullable;

public class ParticleUtils {
    public static <T> void spawnParticleLineInBetweens(Particle particle, World world, Vector3d a, Vector3d b, int middleParticleCount, @Nullable T data) {
        Vector3d segmentOffset = new Vector3d(a).sub(b).div(middleParticleCount + 1);

        Vector3d currentSegment = new Vector3d(a);
        for (int i = 0; i < middleParticleCount; i++) {
            currentSegment.add(segmentOffset);
            world.spawnParticle(particle, currentSegment.x, currentSegment.y, currentSegment.z, 1, data);
        }
    }
}
