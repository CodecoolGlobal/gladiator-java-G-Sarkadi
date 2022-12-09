package com.codecool.gladiator.model.gladiators;

import com.codecool.gladiator.util.RandomUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class GladiatorFactory {

    private List<String> names;
    private final List<GladiatorTypes> gladiatorTypes = new ArrayList<>();
    private static final int MIN_ATTRIBUTE = 25;
    private static final int MAX_ATTRIBUTE = 100;
    private static final int MIN_LEVEL = 1;
    private static final int MAX_LEVEL = 5;

    public GladiatorFactory(String fileOfNames) {
        try {
            File file = new File(getClass().getClassLoader().getResource(fileOfNames).getFile());
            names = Files.readAllLines(file.toPath());
        } catch (IOException|NullPointerException e) {
            System.out.println("Names file not found or corrupted!");
            System.exit(1);
        }
        gladiatorTypes.add(GladiatorTypes.ARCHER);
        gladiatorTypes.add(GladiatorTypes.ASSASSIN);
        gladiatorTypes.add(GladiatorTypes.BRUTAL);
        gladiatorTypes.add(GladiatorTypes.SWORDSMAN);
        gladiatorTypes.add(GladiatorTypes.SWORDSMAN);
    }

    /**
     * Picks a random name from the file given in the constructor
     *
     * @return gladiator name
     */
    private String getRandomName() {
        return names.get(RandomUtils.getRandom().nextInt(names.size()));
    }

    /**
     * Instantiates a new gladiator with random name and type.
     * Creating an Archer, an Assassin, or a Brutal has the same chance,
     * while the chance of creating a Swordsman is the double of the chance of creating an Archer.
     * @return new Gladiator
     */
    public Gladiator generateRandomGladiator() {
        GladiatorTypes gladiatorType = gladiatorTypes.get(RandomUtils.getRandom().nextInt(gladiatorTypes.size()));
        int hp = RandomUtils.getRandom().nextInt(MAX_ATTRIBUTE - MIN_ATTRIBUTE) + MIN_ATTRIBUTE;
        int sp = RandomUtils.getRandom().nextInt(MAX_ATTRIBUTE - MIN_ATTRIBUTE) + MIN_ATTRIBUTE;
        int dex = RandomUtils.getRandom().nextInt(MAX_ATTRIBUTE - MIN_ATTRIBUTE) + MIN_ATTRIBUTE;
        int level = RandomUtils.getRandom().nextInt(MAX_LEVEL - MIN_LEVEL) + MIN_LEVEL;

        Gladiator newGladiator = null;

        switch (gladiatorType) {
            case ASSASSIN:
                newGladiator = new Assassin(getRandomName(), hp, sp, dex, level);
                break;
            case ARCHER:
                newGladiator = new Archer(getRandomName(), hp, sp, dex, level);
                break;
            case BRUTAL:
                newGladiator = new Brutal(getRandomName(), hp, sp, dex, level);
                break;
            case SWORDSMAN:
                newGladiator = new Swordsman(getRandomName(), hp, sp, dex, level);
                break;
        }
        return newGladiator;
    }

    public enum GladiatorTypes {
        ARCHER,
        ASSASSIN,
        BRUTAL,
        SWORDSMAN
    }
}
