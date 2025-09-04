package me.nanorasmus.nanoutils.item;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ItemManager {
    private static final HashMap<Class<? extends AbstractItem>, AbstractItem> itemRegistry = new HashMap<>();

    public static <T extends AbstractItem> void registerHandler(T handler) {
        itemRegistry.put(handler.getClass(), handler);
    }

    public static <T extends AbstractItem> T getHandler(Class<T> handlerClass) {
        AbstractItem handler = itemRegistry.get(handlerClass);

        if (!handler.getClass().equals(handlerClass)) {
            Bukkit.getLogger().severe("Failed to parse handler registered under type \"" + handlerClass.getPackageName() + "\", something must have gone wrong when registering it!");
            return null;
        }

        return (T) handler;
    }
}
