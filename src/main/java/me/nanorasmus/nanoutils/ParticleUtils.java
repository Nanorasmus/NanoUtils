package me.nanorasmus.nanoutils;

import org.bukkit.Particle;
import org.bukkit.World;
import org.joml.Vector3d;

import javax.annotation.Nullable;

public class ParticleUtils {
    public static <T> void spawnParticleLineBetweenPointsExclusive(Particle particle, World world, Vector3d a, Vector3d b, int middleParticleCount, @Nullable T data) {
        Vector3d segmentOffset = new Vector3d(a).sub(b).div(middleParticleCount + 1);

        Vector3d currentSegment = new Vector3d(a);
        for (int i = 0; i < middleParticleCount; i++) {
            currentSegment.add(segmentOffset);
            world.spawnParticle(particle, currentSegment.x, currentSegment.y, currentSegment.z, 1, data);
        }
    }
    public static <T> void spawnParticleLineBetweenPointsInclusive(Particle particle, World world, Vector3d a, Vector3d b, int middleParticleCount, @Nullable T data) {
        world.spawnParticle(particle, a.x, a.y, a.z, 1, data);
        spawnParticleLineBetweenPointsExclusive(particle, world, a, b, middleParticleCount, data);
        world.spawnParticle(particle, b.x, b.y, b.z, 1, data);
    }
}
