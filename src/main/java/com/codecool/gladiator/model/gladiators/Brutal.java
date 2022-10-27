package com.codecool.gladiator.model.gladiators;

public class Brutal extends Gladiator {
    public Brutal(String name, int baseHp, int baseSp, int baseDex, int level) {
        super(name, baseHp, baseSp, baseDex, level);
    }

    @Override
    protected Multiplier getHpMultiplier() {
        // Todo
        return null;
    }

    @Override
    protected Multiplier getSpMultiplier() {
        // Todo
        return null;
    }

    @Override
    protected Multiplier getDexMultiplier() {
        // Todo
        return null;
    }
}
