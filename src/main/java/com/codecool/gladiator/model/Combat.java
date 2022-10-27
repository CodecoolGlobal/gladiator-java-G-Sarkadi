package com.codecool.gladiator.model;

import com.codecool.gladiator.model.gladiators.Gladiator;
import com.codecool.gladiator.util.RandomUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Combat class, used for simulating fights between pairs of gladiators
 */
public class Combat {

    private final Gladiator gladiator1;
    private final Gladiator gladiator2;

    private final List<String> combatLog;

    private final static int MIN_CLAMP = 10;
    private final static int MAX_CLAMP = 100;
    private final static double MIN_ATTACK_MODIFIER = 0.1;
    private final static double MAX_ATTACK_MODIFIER = 0.5;
    private final static int MIN_PERCENT = 1;
    private final static int MAX_PERCENT = 100;

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
                combatLog.add(attacker + " deals " + damage + " damage");
                if (defender.isDead()) {
                    combatLog.add(defender + " has died, " + attacker + " wins!\n");
                    winner = attacker;
                    attacker.levelUp();
                    attacker.healUp();
                    break;
                }
            } else {
                combatLog.add(attacker + " missed");
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
        int chanceOfHitting = attacker.getDex() - defender.getDex();
        if (chanceOfHitting < MIN_CLAMP) {
            chanceOfHitting = MIN_CLAMP;
        } else if (chanceOfHitting > MAX_CLAMP) {
            chanceOfHitting = MAX_CLAMP;
        }
        return RandomUtils.getIntValueBetween(MAX_PERCENT, MIN_PERCENT) <= chanceOfHitting;
    }

    private int calculateAttack(Gladiator attacker, Gladiator defender) {
        double attackModifier = RandomUtils.getDoubleValueBetween(MAX_ATTACK_MODIFIER, MIN_ATTACK_MODIFIER);
        int damage = (int) (attacker.getSp() * attackModifier);
        defender.decreaseHpBy(damage);
        return damage;
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
