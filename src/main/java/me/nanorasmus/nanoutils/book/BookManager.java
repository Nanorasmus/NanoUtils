package me.nanorasmus.nanoutils.book;

import me.nanorasmus.nanoutils.Main;
import me.nanorasmus.nanoutils.data.nbt.ItemStackNBT;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BookManager implements Listener {
    private static HashMap<NamespacedKey, BookEntry> books = new HashMap<>();
    private static BookOpener bookOpener;

    public static void registerBook(NamespacedKey key, BookEntry book) {
        books.put(key, book);
    }

    public static boolean hasBook(NamespacedKey key) {
        return books.containsKey(key);
    }

    public static BookEntry getBook(NamespacedKey key) {
        return books.get(key);
    }

    public static void Init() {
        bookOpener = new BookOpener(Main.main);
    }

    private static class BookOpener implements Listener, CommandExecutor {
        public BookOpener(Main main) {
            main.getServer().getPluginManager().registerEvents(this, main);
            main.getCommand("open_book").setExecutor(this);
        }

        @EventHandler
        public void onCommand(PlayerCommandPreprocessEvent e) {
        }

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            Bukkit.getLogger().info(Arrays.toString(args));
            if (sender instanceof Player && label.equals("nanoutils:open_book")) {
                if (args.length == 0) {
                    // Compile all the registered book IDs
                    ArrayList<String> bookIds = new ArrayList<>();
                    for (NamespacedKey id : books.keySet()) {
                        bookIds.add(id.toString());
                    }

                    sender.sendMessage("Valid book ids: " + String.join(", ", bookIds));
                    return true;
                }

                String book_id = args[0];
                BookEntry book = BookManager.getBook(NamespacedKey.fromString(book_id));

                if (book == null) {
                    sender.sendMessage("Invalid book id! do \"nanoutils:open_book\" to see all registered book ids");
                    return true;
                }

                if (!book.tryOpenBookForPlayer((Player) sender)) {
                    sender.sendMessage("You do not meet the requirements to open that book!");
                }

                return true;
            }
            return false;
        }
    }
}
