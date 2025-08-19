package me.nanorasmus.nanoutils.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.io.Serializable;

public abstract class InteractionEffect implements Serializable {
    public abstract void run(Player interacter, Entity effectHolder);
}
