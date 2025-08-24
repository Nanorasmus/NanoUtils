package me.nanorasmus.nanoutils.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.io.Serializable;

public abstract class DamageEffect implements Serializable {
    public abstract void run(EntityDamageEvent event);
}
