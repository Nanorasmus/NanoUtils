package me.nanorasmus.nanoutils.book;

import me.nanorasmus.nanoutils.data.nbt.EntityNBT;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BookEntry {
    String title;
    ArrayList<BookPage> pages = new ArrayList<>();

    @Nullable Advancement requiredAdvancement;
    ArrayList<NamespacedKey> requiredDataKeys = new ArrayList<>();

    public BookEntry(String title, BookPage... pages) {
        this.title = title;
        insert(pages);
    }

    public BookEntry(String title, Advancement requiredAdvancement, BookPage... pages) {
        this(title, pages);
        this.requiredAdvancement = requiredAdvancement;
    }

    public BookEntry(String title, NamespacedKey requiredDataKey, BookPage... pages) {
        this(title, pages);
        addRequiredDataKeys(requiredDataKey);
    }

    public BookEntry(String title, Advancement requiredAdvancement, NamespacedKey requiredDataKey, BookPage... pages) {
        this(title, requiredAdvancement, pages);
        addRequiredDataKeys(requiredDataKey);
    }

    public void insert(BookPage... newPages) {
        this.pages.addAll(List.of(newPages));
    }

    public void addRequiredDataKeys(NamespacedKey... newRequirements) {
        requiredDataKeys.addAll(List.of(newRequirements));
    }

    public boolean playerMeetsRequirements(Player player) {
        if (requiredAdvancement != null && !player.getAdvancementProgress(requiredAdvancement).isDone()) {
            return false;
        }

        for (NamespacedKey requiredDataKey : requiredDataKeys) {
            Boolean meetsRequirement = EntityNBT.getBooleanFromEntity(requiredDataKey, player);
            if (meetsRequirement == null || !meetsRequirement) {
                return false;
            }
        }

        return true;
    }

    public void openBookForPlayer(Player player) {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);

        BookMeta meta = (BookMeta) book.getItemMeta();
        if (meta == null) {
            Bukkit.getLogger().severe("Failed to open book for player due to newly instantiated book not having any meta?!");
            return;
        }

        for (BookPage page : pages) {
            meta.spigot().addPage(page.compileForPlayer(player));
        }
        book.setItemMeta(meta);

        player.openBook(book);
    }

    public boolean tryOpenBookForPlayer(Player player) {
        if (player == null || !playerMeetsRequirements(player)) {
            return false;
        }

        openBookForPlayer(player);
        return true;
    }
}
