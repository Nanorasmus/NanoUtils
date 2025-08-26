package me.nanorasmus.nanoutils.book;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BookPagePartCombination extends BookPagePart {
    ArrayList<BookPagePart> parts = new ArrayList<>();

    public BookPagePartCombination(BookPagePart... parts) {
        insert(parts);
    }

    public void insert(BookPagePart... parts) {
        this.parts.addAll(List.of(parts));
    }

    @Override
    BaseComponent getContents(Player player) {
        ComponentBuilder builder = new ComponentBuilder();

        for (BookPagePart part : parts) {
            builder.append(part.getContents(player));
        }

        return builder.build();
    }
}
