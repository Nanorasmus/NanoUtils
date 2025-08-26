package me.nanorasmus.nanoutils.book;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

public class BookPagePartReference extends BookPagePart {

    NamespacedKey referenceKey;
    String hoverText = "Click to open.";

    public BookPagePartReference(NamespacedKey referenceKey) {
        this.referenceKey = referenceKey;
    }
    public BookPagePartReference(NamespacedKey referenceKey, String hoverText) {
        this(referenceKey);

        this.hoverText = hoverText;
    }

    @Override
    BaseComponent getContents(Player player) {
        BookEntry reference = BookManager.getBook(referenceKey);

        if (reference.playerMeetsRequirements(player)) {
            ComponentBuilder componentBuilder = new ComponentBuilder().bold(true).color(ChatColor.LIGHT_PURPLE);
            componentBuilder.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/nanoutils:open_book " + referenceKey.toString()));
            componentBuilder.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(hoverText)));
            componentBuilder.append(reference.title);

            return componentBuilder.build();
        } else {
            return new ComponentBuilder().obfuscated(true).append(reference.title).build();
        }


    }
}
