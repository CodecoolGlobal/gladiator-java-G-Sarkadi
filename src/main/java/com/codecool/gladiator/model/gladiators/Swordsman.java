package com.codecool.gladiator.model.gladiators;

public class Swordsman extends Gladiator {
    /**
     * Constructor for Gladiators
     *
     * @param name    the gladiator's name
     * @param baseHp  the gladiator's base Health Points
     * @param baseSp  the gladiator's base Strength Points
     * @param baseDex the gladiator's base Dexterity Points
     * @param level   the gladiator's starting Level
     */
    public Swordsman(String name, int baseHp, int baseSp, int baseDex, int level) {
        super(name, baseHp, baseSp, baseDex, level);
    }

    @Override
    protected Multiplier getHpMultiplier() {
        return Multiplier.Medium;
    }

    @Override
    protected Multiplier getSpMultiplier() {
        return Multiplier.Medium;
    }

    @Override
    protected Multiplier getDexMultiplier() {
        return Multiplier.Medium;
    }

    @Override
    public String getCustomHitMessage(int damage) {
        return "The sword of " + getName() + " was lucky this time and caused " + damage + " damage";
    }

    @Override
    public String getCustomMissMessage() {
        return "The enemy skillfully avoided the stroke of " + getName();
    }
}
