package com.codecool.gladiator.model.gladiators;

public abstract class Gladiator {

    private final String name;
    private final int baseHp;
    private final int baseSp;
    private final int baseDex;
    private int level;
    private int currentHp;

    /**
     * Constructor for Gladiators
     *
     * @param name the gladiator's name
     * @param baseHp the gladiator's base Health Points
     * @param baseSp the gladiator's base Strength Points
     * @param baseDex the gladiator's base Dexterity Points
     * @param level the gladiator's starting Level
     */
    public Gladiator(String name, int baseHp, int baseSp, int baseDex, int level) {
        this.name = name;
        this.baseHp = baseHp;
        this.baseSp = baseSp;
        this.baseDex = baseDex;
        this.level = level;
        this.currentHp = this.getMaxHp();
    }

    /**
     * @return HP multiplier of the gladiator subclass
     */
    protected abstract Multiplier getHpMultiplier();

    /**
     * @return SP multiplier of the gladiator subclass
     */
    protected abstract Multiplier getSpMultiplier();

    /**
     * @return DEX multiplier of the gladiator subclass
     */
    protected abstract Multiplier getDexMultiplier();

    /**
     * @return Gladiator's name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return gladiators level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Increments the gladiators level by 1
     */
    public void levelUp() {
        level++;
    }

    /**
     * Returns the full name of the gladiator
     * assembled by the subtype and the name
     * (e.g. "Brutal Brutus" or "Archer Leo")
     *
     * @return the full name
     */
    public String getFullName() {
        return this.getClass().getSimpleName() + " " + this.name;
    }

    /**
     * Calculate and return the max HP
     * @return max HP
     */
    public int getMaxHp() {
        return (int) (baseHp * getHpMultiplier().getValue() * level);
    }

    /**
     * Calculate and return the max SP
     * @return max SP
     */
    public int getMaxSp() {
        return (int) (baseSp * getSpMultiplier().getValue() * level);
    }

    /**
     * Calculate and get the max dexterity
     * @return max dex
     */
    public int getMaxDex() {
        return (int) (baseDex * getDexMultiplier().getValue() * level);
    }

    public void decreaseHpBy(int attack) {
        currentHp -= attack;
    }

    public boolean isDead() {
        return currentHp <= 0;
    }

    public int getHp() {
        return baseHp;
    }

    public int getSp() {
        return getMaxSp();
    }

    public int getDex() {
        return getMaxDex();
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void healUp() {
        currentHp = getMaxHp();
    }

    public enum Multiplier {
        Low(0.75),
        Medium(1.0),
        High(1.25);

        private final double value;

        Multiplier(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }
    }

    @Override
    public String toString() {
        return getFullName() +
                " (" + currentHp + "/" + currentHp + " HP, " +
                getMaxSp() + " SP, " +
                getMaxDex() + " DEX, " +
                getLevel() + " LVL)";
    }
}
