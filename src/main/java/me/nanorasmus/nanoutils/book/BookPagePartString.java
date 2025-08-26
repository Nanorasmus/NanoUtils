package me.nanorasmus.nanoutils.book;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.entity.Player;

public class BookPagePartString extends BookPagePart {
    String contents;

    public BookPagePartString(String contents) {
        this.contents = contents;
    }

    @Override
    BaseComponent getContents(Player player) {
        return new ComponentBuilder().append(contents).build();
    }
}
