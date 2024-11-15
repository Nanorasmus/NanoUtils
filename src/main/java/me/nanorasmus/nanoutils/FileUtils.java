package me.nanorasmus.nanoutils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static Gson gson;

    public static String folderPath;
    static File folder;

    public static void Init() {
        gson = new GsonBuilder().setPrettyPrinting().create();

        folderPath = Main.plugin.getDataFolder().getParentFile().getAbsolutePath() + "/Nano/";
        folder = new File(folderPath);
        boolean dirMade = folder.mkdirs();
    }

    public static boolean Exists(String path) {
        File file = new File(folderPath + path);
        return file.exists();
    }

    public static String Read(String path) {
        File file = new File(folderPath + path);
        try {
            return Files.readString(Path.of(folderPath + path));
        } catch (IOException e) {
            return null;
        }
    }

    public static void Write(String path, String content) {
        File file = new File(folderPath + path);
        file.getParentFile().mkdirs();
        try {
            file.delete();
            Files.write(file.toPath(), content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void SoftWrite(String path, String content) {
        File file = new File(folderPath + path);
        if (file.exists()) {
            return;
        }
        file.getParentFile().mkdirs();
        try {
            file.delete();
            Files.write(file.toPath(), content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static void Save(String path, Object content) {
        String json = gson.toJson(content);
        File file = new File(folderPath + path);
        file.getParentFile().mkdirs();
        try {
            file.delete();
            Files.write(file.toPath(), json.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void SoftSave(String path, Object content) {
        File file = new File(folderPath + path);
        if (file.exists()) {
            return;
        }
        file.getParentFile().mkdirs();
        try {
            file.delete();
            String json = gson.toJson(content);
            Files.write(file.toPath(), json.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object Load(String path) {
        File file = new File(folderPath + path);
        try {
            return gson.fromJson(Files.readString(Path.of(folderPath + path)), Object.class);
        } catch (IOException e) {
            return null;
        }
    }

    public static <T> T Load(String path, T backup) {
        File file = new File(folderPath + path);
        try {
            return (T) gson.fromJson(Files.readString(Path.of(folderPath + path)), backup.getClass());
        } catch (IOException e) {
            Bukkit.getLogger().info("Something went wrong with loading a file!");
            e.printStackTrace();
            return backup;
        }
    }

    public static <T> List<T> LoadAll(String path, List<T> backup) {
        // Get folder and check for null
        File[] files = new File(folderPath + path).listFiles();
        if (files == null) {
            return backup;
        }

        // Gather and parse all JSON files from folder
        List<T> output = new ArrayList<>();

        for (File file : files) {
            // Check if it is a json file
            if (!file.isFile() || !file.getName().trim().endsWith(".json")) continue;
            output.add(Load(path + "/" + file.getName(), backup.get(0)));
        }

        // Return
        return output;
    }
}
