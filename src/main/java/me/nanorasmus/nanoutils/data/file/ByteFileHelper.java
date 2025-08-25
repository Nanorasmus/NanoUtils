package me.nanorasmus.nanoutils.data.file;

import com.esotericsoftware.kryo.kryo5.Kryo;
import com.esotericsoftware.kryo.kryo5.io.Input;
import com.esotericsoftware.kryo.kryo5.io.Output;
import com.esotericsoftware.kryo.kryo5.serializers.DefaultSerializers;
import com.esotericsoftware.kryo.kryo5.util.Null;
import me.nanorasmus.nanoutils.Main;
import me.nanorasmus.nanoutils.data.SerializationHelper;
import org.bukkit.Bukkit;

import javax.annotation.Nullable;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ByteFileHelper {
    public static String folderPath;
    static File folder;


    public static void Init() {
        folderPath = Main.plugin.getDataFolder().getParentFile().getAbsolutePath() + "/Nano/Data/";
        folder = new File(folderPath);
        boolean dirMade = folder.mkdirs();
    }

    public static boolean Exists(String path) {
        File file = new File(folderPath + path);
        return file.exists();
    }

    public static void Save(String path, Object content) {
        File file = new File(folderPath + path);
        file.getParentFile().mkdirs();
        try {
            file.delete();

            byte[] bytes = SerializationHelper.serialize(content);

            if (bytes != null) {
                Files.write(file.toPath(), bytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            }
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

            byte[] bytes = SerializationHelper.serialize(content);

            if (bytes != null) {
                Files.write(file.toPath(), bytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T Load(String path, T backup) {
        File file = new File(folderPath + path);
        try {
            Object obj = SerializationHelper.deserialize(Files.readAllBytes(Path.of(folderPath + path)), backup.getClass());

            return (T) SerializationHelper.deserialize(Files.readAllBytes(Path.of(folderPath + path)), backup.getClass());
        } catch (IOException e) {
            Bukkit.getLogger().severe("Something went wrong with loading a file!");
            e.printStackTrace();
            return backup;
        } catch (ClassCastException e) {
            Bukkit.getLogger().severe("Loaded object from file was not of expected class!");
            e.printStackTrace();
            return backup;
        }
    }

    public static <T> List<T> LoadAll(String path, T backup) {
        // Get folder and check for null
        File[] files = new File(folderPath + path).listFiles();
        if (files == null) {
            return List.of(backup);
        }

        // Gather and parse all files from folder
        List<T> output = new ArrayList<>();

        for (File file : files) {
            // Check if it is a json file
            if (!file.isFile()) continue;
            output.add(Load(path + "/" + file.getName(), backup));
        }

        // Return
        return output;
    }
}
