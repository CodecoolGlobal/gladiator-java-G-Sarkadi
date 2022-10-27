package com.codecool.gladiator.model.gladiators;

public class Assassin extends Gladiator {
    /**
     * Constructor for Gladiators
     *
     * @param name    the gladiator's name
     * @param baseHp  the gladiator's base Health Points
     * @param baseSp  the gladiator's base Strength Points
     * @param baseDex the gladiator's base Dexterity Points
     * @param level   the gladiator's starting Level
     */
    public Assassin(String name, int baseHp, int baseSp, int baseDex, int level) {
        super(name, baseHp, baseSp, baseDex, level);
    }

    @Override
    protected Multiplier getHpMultiplier() {
        return Multiplier.Low;
    }

    @Override
    protected Multiplier getSpMultiplier() {
        return Multiplier.High;
    }

    @Override
    protected Multiplier getDexMultiplier() {
        return Multiplier.High;
    }

    @Override
    public String getCustomHitMessage(int damage) {
        return "The sneaky attack of " + getName() + " caused " + damage + " damage!";
    }

    @Override
    public String getCustomMissMessage() {
        return "The dagger of " + getName() + " couldn't find the target.";
    }
}
