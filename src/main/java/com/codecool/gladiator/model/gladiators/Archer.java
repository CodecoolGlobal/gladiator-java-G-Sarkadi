package com.codecool.gladiator.model.gladiators;

public class Archer extends Gladiator{
    /**
     * Constructor for Gladiators
     *
     * @param name    the gladiator's name
     * @param baseHp  the gladiator's base Health Points
     * @param baseSp  the gladiator's base Strength Points
     * @param baseDex the gladiator's base Dexterity Points
     * @param level   the gladiator's starting Level
     */
    public Archer(String name, int baseHp, int baseSp, int baseDex, int level) {
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
        return Multiplier.High;
    }
}
