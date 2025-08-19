package me.nanorasmus.nanoutils.entity;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class TestEffect extends InteractionEffect {
    final boolean IS_PIG;
    int pet_count = 0;


    public TestEffect(Entity effectHolder) {
        IS_PIG = effectHolder.getType() == EntityType.PIG;
    }

    @Override
    public void run(Player interacter, Entity effectHolder) {
        String toSay;

        if (IS_PIG) {
            toSay = "Oink ";
        } else {
            toSay = "I like pats ";
        }

        Bukkit.broadcastMessage(toSay + pet_count);
        pet_count += 1;
    }
}
