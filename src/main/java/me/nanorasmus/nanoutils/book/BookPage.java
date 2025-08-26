package me.nanorasmus.nanoutils.book;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BookPage {
    ArrayList<BookPagePart> parts = new ArrayList<>();

    public BookPage(BookPagePart... parts) {
        addParts(parts);
    }

    public BookPage(String contents) {
        addParts(new BookPagePartString(contents));
    }

    public void addParts(BookPagePart... parts) {
        this.parts.addAll(List.of(parts));
    }

    public BaseComponent[] compileForPlayer(Player player) {
        BaseComponent[] output = new BaseComponent[parts.size()];

        for (int i = 0; i < parts.size(); i++) {
            output[i] = parts.get(i).getContents(player);
        }

        return output;
    }
}
