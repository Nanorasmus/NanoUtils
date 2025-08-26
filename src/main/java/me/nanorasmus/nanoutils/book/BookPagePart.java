package me.nanorasmus.nanoutils.book;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;

public abstract class BookPagePart {
    abstract BaseComponent getContents(Player player);
}
