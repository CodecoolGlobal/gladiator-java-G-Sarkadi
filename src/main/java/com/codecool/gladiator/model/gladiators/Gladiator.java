package com.codecool.gladiator.model.gladiators;

import com.codecool.gladiator.util.RandomUtils;


public abstract class Gladiator {

    private final String name;
    private final int baseHp;
    private final int baseSp;
    private final int baseDex;
    private int level;
    private int currentHp;
    private WeaponEffect weaponEffect;
    private static final int WEAPON_EFFECT_CHANCE = 10;
    private int bleedings = 0;
    private int poisonedDuration = 0;
    private int poisonedTimes = 0;
    private int burningDuration = 0;
    private int paralyzedDuration = 0;

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
        handleWeaponEffect();
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

    public abstract String getCustomHitMessage(int damage);

    public abstract String getCustomMissMessage();

    public void setBleeding(int bleedings) {
        this.bleedings = bleedings;
    }

    public void setPoisonedDuration(int poisoned) {
        this.poisonedDuration = poisoned;
    }

    public void setPoisonedTimes() {
        this.poisonedTimes++;
    }

    public void setBurning(int burningDuration) {
        this.burningDuration += burningDuration;
    }

    public void setParalyzed(int paralyzedDuration) {
        this.paralyzedDuration += paralyzedDuration;
    }

    public int getBleedings() {
        return bleedings;
    }

    public int getPoisonedDuration() {
        return poisonedDuration;
    }

    public int getPoisonedTimes() {
        return poisonedTimes;
    }

    public int getBurningDuration() {
        return burningDuration;
    }

    public int getParalyzedDuration() {
        return paralyzedDuration;
    }

    public void recuperate() {
        currentHp = getMaxHp();
        bleedings = 0;
        poisonedDuration = 0;
        poisonedTimes = 0;
        burningDuration = 0;
        paralyzedDuration = 0;
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

    public enum WeaponEffect {
        BLEEDING,
        POISON,
        BURNING,
        PARALYZING,
        CRITICAL_HIT;

        public static WeaponEffect getRandomWeaponEffect() {
            WeaponEffect[] weaponEffects = values();
            return weaponEffects[RandomUtils.getRandom().nextInt(weaponEffects.length)];
        }
    }


    private void handleWeaponEffect() {
        boolean isWeaponEffect = RandomUtils.getChance(WEAPON_EFFECT_CHANCE);
        if (isWeaponEffect) {
            this.weaponEffect = WeaponEffect.getRandomWeaponEffect();
        }
    }

    public WeaponEffect getWeaponEffect() {
        return weaponEffect;
    }

    @Override
    public String toString() {
        String weaponEffectString = "";
        if (weaponEffect != null) {
            weaponEffectString = ", Weapon effect: " + weaponEffect;
        }
        return getFullName() +
                " (" + currentHp + "/" + currentHp + " HP, " +
                getMaxSp() + " SP, " +
                getMaxDex() + " DEX, " +
                getLevel() + " LVL" + weaponEffectString + ")";
    }
}
