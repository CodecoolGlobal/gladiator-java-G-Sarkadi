package com.codecool.gladiator.model.gladiators;

public class Brutal extends Gladiator {
    public Brutal(String name, int baseHp, int baseSp, int baseDex, int level) {
        super(name, baseHp, baseSp, baseDex, level);
    }

    @Override
    protected Multiplier getHpMultiplier() {
        return Multiplier.High;
    }

    @Override
    protected Multiplier getSpMultiplier() {
        return Multiplier.High;
    }

    @Override
    protected Multiplier getDexMultiplier() {
        return Multiplier.Low;
    }

    @Override
    public String getCustomHitMessage(int damage) {
        return "The blade of " + getName() + " pounced the enemy and dealt " + damage + " damage";
    }

    @Override
    public String getCustomMissMessage() {
        return getName() + " was too slow ant the opponent deflected the stroke";
    }
}
