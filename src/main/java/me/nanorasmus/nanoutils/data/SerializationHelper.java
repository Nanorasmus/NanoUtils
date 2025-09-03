package me.nanorasmus.nanoutils.data;

import com.esotericsoftware.kryo.kryo5.Kryo;
import com.esotericsoftware.kryo.kryo5.io.Input;
import com.esotericsoftware.kryo.kryo5.io.Output;
import com.esotericsoftware.kryo.kryo5.serializers.DefaultSerializers;
import me.nanorasmus.nanoutils.Main;

import javax.annotation.Nullable;
import java.io.*;
import java.util.UUID;

public class SerializationHelper {
    public static Kryo internalKryo;

    public static void Init() {
        // Initialize Kryo
        internalKryo = new Kryo();
        internalKryo.setRegistrationRequired(false);
        internalKryo.setReferences(true);
        internalKryo.register(UUID.class, new DefaultSerializers.UUIDSerializer());
    }

    @Nullable
    public static byte[] serialize(Object obj, Kryo kryo) {
        try {
            Output output = new Output(1024, -1);
            kryo.writeObject(output, obj);
            return output.toBytes();
        } catch (Exception err) {
            // Kryo serialization failed
            err.printStackTrace();
            Main.main.getLogger().info("Kryo serialization failed! Attempting base Java serialization...");
            return serializeFallback(obj);
        }
    }

    @Nullable
    private static byte[] serializeFallback(Object obj) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

        try (ObjectOutputStream objStream = new ObjectOutputStream(byteStream)) {
            objStream.writeObject(obj);
            objStream.flush();
            return byteStream.toByteArray();
        } catch (Exception err) {
            Main.main.getLogger().severe("Failed to serialize object: " + obj);
            err.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static <T> T deserialize(byte[] bytes, Class<T> type, Kryo kryo) {
        try {
            Input input = new Input(bytes);
            return kryo.readObject(input, type);
        } catch (Exception err) {
            err.printStackTrace();
            Main.main.getLogger().info("Kryo deserialization failed! Attempting base Java deserialization...");
            try {
                return (T) deserializeFallback(bytes);
            } catch (ClassCastException err2) {
                Main.main.getLogger().severe("Base java deserialization succeeded! But deserialized value did not match expected type. Returning null...");
                err.printStackTrace();
                return null;
            }
        }
    }

    @Nullable
    private static Object deserializeFallback(byte[] bytes) {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);

        try (ObjectInput obj = new ObjectInputStream(byteStream)) {
            return obj.readObject();
        }  catch (Exception err) {
            Main.main.getLogger().severe("Failed to deserialize object!");
            err.printStackTrace();
            return null;
        }
    }
}
