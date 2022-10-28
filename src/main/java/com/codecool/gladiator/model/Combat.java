package com.codecool.gladiator.model;

import com.codecool.gladiator.model.gladiators.Gladiator;
import com.codecool.gladiator.util.RandomUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Combat class, used for simulating fights between pairs of gladiators
 */
public class Combat {
    private static final int BLEEDING_CHANCE = 5;
    private static final int POISON_CHANCE = 20;
    private static final int BURNING_CHANCE = 15;
    private static final int PARALYZING_CHANCE = 10;
    private static final int MIN_BURN_DURATION = 1;
    private static final int MAX_BURN_DURATION = 5;
    private static final int PARALYZED_DURATION = 3;
    private final static int MIN_CLAMP = 10;
    private final static int MAX_CLAMP = 100;
    private final static double MIN_ATTACK_MODIFIER = 0.1;
    private final static double MAX_ATTACK_MODIFIER = 0.5;
    private final static int MIN_PERCENT = 1;
    private final static int MAX_PERCENT = 100;
    private static final int POISON_DURATION = 3;
    private static final int LETHAL_POISON_TIMES = 2;

    private final Gladiator gladiator1;
    private final Gladiator gladiator2;

    private final List<String> combatLog;

    public Combat(Contestants contestants) {
        this.gladiator1 = contestants.gladiator1;
        this.gladiator2 = contestants.gladiator2;
        this.combatLog = new ArrayList<>();
    }

    /**
     * Simulates the combat and returns the winner.
     * If one of the opponents is null, the winner is the one that is not null
     * If both of the opponents are null, the return value is null
     *
     * @return winner of combat
     */
    public Gladiator simulate() {
        // null-check on gladiators
        if (gladiator1 == null && gladiator2 == null ) {
            return null;
        } else if (gladiator1 == null) {
            return gladiator2;
        } else if (gladiator2 == null) {
            return gladiator1;
        }

        // select first attacker
        boolean starter = RandomUtils.getRandom().nextBoolean();
        Gladiator attacker = starter ? gladiator1 : gladiator2;
        Gladiator defender = starter ? gladiator2 : gladiator1;
        Gladiator winner;
        // attack until someone dies
        while (true) {
            boolean isThereAHit = isThereAHit(attacker,defender);
            if (isThereAHit) {
                int damage = calculateAttack(attacker, defender);

                // check if defender already poisoned, if yes, dies immediately
                if (defender.getPoisonedTimes() >= LETHAL_POISON_TIMES) {
                    combatLog.add(defender.getName() + " was poisoned a second time, died immediately");
                    winner = attacker;
                    attacker.levelUp();
                    attacker.recuperate();
                    defender.recuperate();
                    break;
                }

                defender.decreaseHpBy(damage);
                combatLog.add(attacker.getCustomHitMessage(damage));
                if (defender.isDead()) {
                    winner = attacker;
                    attacker.levelUp();
                    attacker.recuperate();
                    break;
                }
            } else {
                combatLog.add(attacker.getCustomMissMessage());
            }
            attacker = switchRoles(attacker);
            defender = switchRoles(defender);
        }
        return winner;
    }

    private Gladiator switchRoles(Gladiator gladiator) {
        return gladiator.equals(gladiator1) ? gladiator2 : gladiator1;
    }

    private boolean isThereAHit(Gladiator attacker, Gladiator defender) {


        // TODO paralyzing, defence


        int chanceOfHitting = attacker.getDex() - defender.getDex();
        if (chanceOfHitting < MIN_CLAMP) {
            chanceOfHitting = MIN_CLAMP;
        } else if (chanceOfHitting > MAX_CLAMP) {
            chanceOfHitting = MAX_CLAMP;
        }
        return RandomUtils.getIntValueBetween(MAX_PERCENT, MIN_PERCENT) <= chanceOfHitting;
    }

    private int calculateAttack(Gladiator attacker, Gladiator defender) {
        Gladiator.WeaponEffect weaponEffect = attacker.getWeaponEffect();
        boolean isHitByWeaponEffect = false;
        if (weaponEffect != null) {
            isHitByWeaponEffect = isHitByWeaponEffect(weaponEffect);
        }
        if (isHitByWeaponEffect) {
            addWeaponEffectToAffectedGladiator(weaponEffect, defender);
        }
        int weaponEffectDamage = calculateWeaponEffectDamage(defender);

        // TODO handle Weapon effects


        // TODO paralyzing, attack

        double attackModifier = RandomUtils.getDoubleValueBetween(MAX_ATTACK_MODIFIER, MIN_ATTACK_MODIFIER);
        int baseDamage = (int) (attacker.getSp() * attackModifier);





        return baseDamage + weaponEffectDamage;
    }

    private boolean isHitByWeaponEffect(Gladiator.WeaponEffect weaponEffect) {
        switch (weaponEffect) {
            case BLEEDING:
                return RandomUtils.getChance(BLEEDING_CHANCE);
            case POISON:
                return RandomUtils.getChance(POISON_CHANCE);
            case BURNING:
                return RandomUtils.getChance(BURNING_CHANCE);
            case PARALYZING:
                return RandomUtils.getChance(PARALYZING_CHANCE);
            default:
                return false;
        }
    }

    private void addWeaponEffectToAffectedGladiator(Gladiator.WeaponEffect weaponEffect, Gladiator defender) {
        switch (weaponEffect) {
            case BLEEDING:
                // 2% damage in every turn, can have multiple bleedings with cumulative damage
                defender.setBleeding(1);
                combatLog.add(defender.getName() + " is bleeding!");
                break;
            case POISON:
                // 5% damage for 3 turns, if poisoned again, dies immediately
                defender.setPoisonedDuration(POISON_DURATION);
                defender.setPoisonedTimes();
                combatLog.add(defender.getName() + " is poisoned!");
                break;
            case BURNING:
                // for 1-5 turns 5% damage, if burns again, the duration extended for 1-5 turns
                int burnDuration = RandomUtils.getIntValueBetween(MAX_BURN_DURATION, MIN_BURN_DURATION);
                defender.setBurning(burnDuration);
                combatLog.add(defender.getName() + " is burning!");
                break;
            case PARALYZING:
                // unable to attack or defend for 3 turns, if paralyzed again, it is reset to 3 turns
                defender.setParalyzed(PARALYZED_DURATION);
                combatLog.add(defender.getName() + " is paralyzed!");
                break;
        }
    }

    private int calculateWeaponEffectDamage(Gladiator defender) {
        double damage = 0;

        // Bleeding, 2% damage in every turn, can have multiple bleedings with cumulative damage
        int bleedings = defender.getBleedings();
        if (bleedings > 0) {
            for (int i = 0; i < bleedings; i++) {
                damage += defender.getCurrentHp() * 0.02;
            }
            combatLog.add(defender.getName() + " suffered " + (int) damage + " damage from bleeding");
            return (int) damage;
        }

        // Poisoned, 5% damage for 3 turns
        int poisonDuration = defender.getPoisonedDuration();
        if (poisonDuration > 0) {
            damage += defender.getCurrentHp() * 0.05;
            defender.setPoisonedDuration(--poisonDuration);
            combatLog.add(defender.getName() + " suffered " + (int)damage + " damage from poison");
            return (int) damage;
        }

        // Burning, 5% damage for random turns
        int burningDuration = defender.getBurningDuration();
        if (burningDuration > 0) {
            damage += defender.getCurrentHp() * 0.05;
            defender.setBurning(-1);
            combatLog.add(defender.getName() + " suffered " + (int)damage + " damage from burning");
            return (int) damage;
        }

        return 0;
    }


    public Gladiator getGladiator1() {
        return gladiator1;
    }

    public Gladiator getGladiator2() {
        return gladiator2;
    }

    public String getCombatLog(String separator) {
        return String.join(separator, combatLog);
    }

}
