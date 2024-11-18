package me.nanorasmus.nanoutils.temp;

import me.nanorasmus.nanoutils.display.DisplayLineHelper;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BeeTurret {
    public boolean isDead = false;
    public Location location;

    boolean active;
    Entity target;
    Chunk chunkCache;

    // Distances
    public double aggroDistance = 40;
    public double reach = 25;

    // Shooting
    public float shootingCooldown = 2f;
    public float shootingRestValue = 0.5f;
    float shootingTimer = 0f;
    ProjectileSource mySource = new ProjectileSource() {
        @Override
        public <T extends Projectile> T launchProjectile(Class<? extends T> projectile) {
            return null;
        }

        @Override
        public <T extends Projectile> T launchProjectile(Class<? extends T> projectile, Vector velocity) {
            return null;
        }
    };

    public BeeTurret(Location loc) {
        location = loc;

        active = false;
    }

    /**
     * Tick logic
     */
    public void tick() {
        if (isDead) {
            return;
        }

        // Get and cache the chunk if it is missing
        if (chunkCache == null) {
            chunkCache = location.getChunk();
        }

        // Only do logic if loaded in
        if (!chunkCache.isLoaded()) {
            // Deactivation logic if it was active upon being unloaded
            if (active) {
                deactivate();
            }

            return;
        }

        graphicsTick();

        if (active) {
            // Make sure we have a valid target
            if (target == null || target.isDead() || !target.isInWorld()) {
                deactivate();
                return;
            }

            // Make sure the target is within looking range
            double targetDistance = target.getLocation().distance(location);
            if (targetDistance > aggroDistance) {
                deactivate();
                return;
            }

            Location locationOffset = target.getLocation().subtract(location);
            locationOffset.setY(0);

            aimDir = locationOffset.toVector().toVector3d().normalize();

            // Shooting logic
            if (targetDistance < reach) {
                shootingTimer += 0.05f;
                if (shootingTimer >= shootingCooldown) {
                    shootingTimer -= shootingCooldown;
                    shootAtTarget();
                }
            } else {
                shootingTimer = Math.min(shootingRestValue, shootingTimer += 0.05f);
            }

            // Destruction logic
            for (Entity potentialDestroyer : location.getWorld().getNearbyEntities(location, 1.5, 1.5, 1.5)) {
                if (potentialDestroyer instanceof AbstractArrow && ((AbstractArrow) potentialDestroyer).getShooter() != mySource) {
                    potentialDestroyer.remove();
                    destroy();
                }
            }
        } else {
            // TODO Find a target (Temporarily just getting the nearest player if they are within a certain distance)
            for (Player p : location.getWorld().getPlayers()) {
                double distance = p.getLocation().distance(location);
                if (distance < aggroDistance && distance > 0.01 && p.getGameMode() == GameMode.SURVIVAL) {
                    target = p;
                    shootingCooldown = shootingRestValue;
                    active = true;
                    break;
                }
            }
        }
    }

    public Vector3d aimDir = new Vector3d();
    Vector3d aimDirSmoothed = new Vector3d();
    ArrayList<BlockDisplay> graphicParts = new ArrayList<>();
    /**
     * Handles the particles of the turret
     */
    void graphicsTick() {
        // Update AimDirSmoothed
        if (!aimDirSmoothed.equals(aimDir)) {
            // Move toward aimDir by 0.2 blocks
            aimDirSmoothed.add(
                    // Making a new vector based on aimDir, then subtracting aimDirSmoothed to get the offset, normalizing and multiplying by 0.2 to get the direction with a 0.2 block magnitude
                    new Vector3d(aimDir).sub(aimDirSmoothed).normalize().mul(0.2)
            );

            // Snap if within 0.22 blocks
            if (aimDirSmoothed.distanceSquared(aimDir) < 0.05) {
                aimDirSmoothed.set(aimDir);
            }
        }

        // If the length is 0 then the turret is inactive and should not be rendered
        if (aimDirSmoothed.lengthSquared() <= 0.025) {
            for (Display part : graphicParts) {
                part.remove();
            }
            graphicParts.clear();
            return;
        }

//        if (breathingIn) {
//            breathingTimer += breathingSpeed;
//            if (breathingTimer >= maxBreath) {
//                breathingIn = false;
//            }
//        } else {
//            breathingTimer -= breathingSpeed;
//            if (breathingTimer <= -0.5) {
//                breathingIn = true;
//            }
//        }

        // Get the right angle of the aimDir and multiply it as to get the correct scale
        Vector3d baseUp = aimDirSmoothed.equals(0, 1, 0) ? new Vector3d(0.001, 1, 0) : new Vector3d(0, 1, 0);
        Vector3d right = new Vector3d(aimDirSmoothed).cross(baseUp).normalize().mul(0.4330125 * aimDirSmoothed.length());
        // Get the up angle
        Vector3d up = new Vector3d(right).cross(aimDirSmoothed);
        Vector3d halfUp = new Vector3d(up).mul(0.5);

        // Calculate the points
        List<Vector3d> points = Arrays.asList(
                new Vector3d(up),
                new Vector3d(right).add(halfUp),
                new Vector3d(right).sub(halfUp),
                new Vector3d(up).mul(-1),
                new Vector3d(halfUp).mul(-1).sub(right),
                new Vector3d(halfUp).sub(right)
        );


        // Convert the points from local to world space
        float cooldownScaling = (float) (Math.pow(shootingTimer - 0.8, 4) / 1.5 + 0.8);
        for (Vector3d point : points) {
            point.mul(cooldownScaling * 3);
            point.add(location.getX(), location.getY(), location.getZ());
        }
        Bukkit.getLogger().info(points + "");

        // Spawn the particles
        for (int i = 0; i < points.size(); i++) {
            // Get the current point
            Vector3d point = points.get(i);

            // Get the last point with loop-around
            Vector3d last = i == 0 ? points.get(points.size() - 1) : points.get(i - 1);

            Bukkit.getLogger().info(point.isFinite() + ", " + last.isFinite() + ", " + new Vector3d(point).sub(last) + ", " + aimDir.length() + ", " + aimDirSmoothed.length());

            // Handle new lines
            if (i >= graphicParts.size()) {
                graphicParts.add(DisplayLineHelper.createLine(location.getWorld(), point, last, Material.HONEY_BLOCK, 0.1f, 0.04f));
                continue;
            }

            // Update existing lines
            DisplayLineHelper.translateLine(graphicParts.get(i), last, point, 0.5f + 0.001f * i, 0.15f);
        }

    }

    /** Shooting logic
     *
     */
    void shootAtTarget() {
        Vector shootingDirection = target.getLocation().add(0, target.getHeight(), 0).subtract(location).toVector().normalize();
        Arrow arrow = location.getWorld().spawnArrow(location, shootingDirection.add(new Vector(0, 0.1, 0)), 1.5f, 2);
        arrow.setColor(Color.ORANGE);
        arrow.setPickupStatus(AbstractArrow.PickupStatus.CREATIVE_ONLY);
        arrow.setShooter(mySource);
    }

    /**
     * Logic for when the turret deactivates.
     * This is triggered when the turret stops aggro, whether by no players being near, or by the chunk being unloaded.
     */
    void deactivate() {
        aimDir = new Vector3d();
        active = false;
    }

    void destroy() {
        for (BlockDisplay part : graphicParts) {
            part.remove();
        }
        graphicParts.clear();
        isDead = true;

        // Explode
        location.getWorld().createExplosion(location, 2, false, false);
    }

}
