package com.codecool.gladiator.util;

import java.util.Random;

public class RandomUtils {

    private static final Random RANDOM = new Random();
    private static final int SPARING_CHANCE = 25;
    private static final int MAX_PERCENT = 101;
    private static final int MIN_PERCENT = 1;
    private static final int WEAPON_EFFECT_CHANCE = 10;

    public static Random getRandom() {
        return RANDOM;
    }

    public static int getIntValueBetween(int max, int min) {
        return RANDOM.nextInt(max - min) + min;
    }

    public static double getDoubleValueBetween(double max, double min) {
        return ((max - min) + min ) * RANDOM.nextDouble();
    }

    public static boolean isSpared() {
        return getIntValueBetween(MAX_PERCENT, MIN_PERCENT) <= SPARING_CHANCE;
    }

    public static boolean isWeaponEffect() {
        return getIntValueBetween(MAX_PERCENT, MIN_PERCENT) <= WEAPON_EFFECT_CHANCE;
    }
}
