package com.codecool.gladiator.util;

import java.util.Random;

public class RandomUtils {

    private static final Random RANDOM = new Random();
    public static Random getRandom() {
        return RANDOM;
    }

    public static int getIntValueBetween(int max, int min) {
        return RANDOM.nextInt(max - min) + min;
    }

    public static double getDoubleValueBetween(double max, double min) {
        return ((max - min) + min ) * RANDOM.nextDouble();
    }
}
