package me.nanorasmus.nanoutils.random;

public class RandomUtils {
    public static int getRandomInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    public static float getRandomFloat(float min, float max) {
        return (float) ((Math.random() * (max - min)) + min);
    }
    public static double getRandomDouble(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }
}
