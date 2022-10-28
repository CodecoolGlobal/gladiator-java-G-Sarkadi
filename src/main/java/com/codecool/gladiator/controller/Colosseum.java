package com.codecool.gladiator.controller;

import com.codecool.gladiator.model.Combat;
import com.codecool.gladiator.model.Contestants;
import com.codecool.gladiator.model.gladiators.*;
import com.codecool.gladiator.util.RandomUtils;
import com.codecool.gladiator.util.Tournament;
import com.codecool.gladiator.view.Viewable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Colosseum {

    public static final int MIN_TOURNAMENT_STAGES = 1;
    public static final int MAX_TOURNAMENT_STAGES = 10;
    private static final int SPARING_CHANCE = 25;

    private final Viewable view;
    private final GladiatorFactory gladiatorFactory;
    private int stages = 2;
    private final boolean sparing;
    private List<Gladiator> survivingGladiators = new ArrayList<>();

    public Colosseum(Viewable view, GladiatorFactory gladiatorFactory) {
        this.view = view;
        this.gladiatorFactory = gladiatorFactory;
        this.sparing = true;
    }

    public Colosseum(Viewable view, List<Gladiator> survivingGladiators) {
        this.view = view;
        this.sparing = false;
        this.gladiatorFactory = null;
        this.survivingGladiators = survivingGladiators;
    }

    /**
     * Runs the Tournament simulation
     */
    public void runSimulation() {
        var numberOfGladiators = (int) Math.pow(2, stages);
        var gladiators = generateGladiators(numberOfGladiators);
        var contestants = splitGladiatorsIntoPairs(gladiators);
        var tournamentTree = new Tournament(contestants);
        var champion = getChampion(tournamentTree);
        announceChampion(champion);

        // The following line chains the above lines:
        // announceChampion(getChampion(new BinaryTree<>(generateGladiators((int) Math.pow(2, stages)))));
    }

    /**
     * Runs the second Tournament with the survivors of the first
     */
    public void runSecondarySimulation() {
        welcome();
        if (survivingGladiators.isEmpty()) {
            announceNoTournament();
            return;
        } else if (survivingGladiators.size() == 1) {
            Gladiator soleSurvivor = survivingGladiators.get(0);
            soleSurvivor.healUp();
            announceSingleContestant(soleSurvivor);
            return;
        }
        introduceGladiators(survivingGladiators);
        var contestants = splitGladiatorsIntoPairs(survivingGladiators);
        var tournamentTree = new Tournament(contestants);
        var champion = getChampion(tournamentTree);
        announceChampion(champion);
    }

    private List<Gladiator> generateGladiators(int numberOfGladiators) {
        List<Gladiator> gladiators = new ArrayList<>();
        for (int i = 0; i < numberOfGladiators; i++) {
            gladiators.add(gladiatorFactory.generateRandomGladiator());
        }
        introduceGladiators(gladiators);
        return gladiators;
    }

    private List<Contestants> splitGladiatorsIntoPairs(List<Gladiator> gladiators) {
        List<Contestants> contestants = new LinkedList<>();
        while (!gladiators.isEmpty()) {
            Gladiator gladiator1 = gladiators.get(0);
            gladiators.remove(gladiator1);
            Gladiator gladiator2 = null;
            if (!gladiators.isEmpty()) {
                gladiator2 = gladiators.get(0);
                gladiators.remove(gladiator2);
            }
            contestants.add(new Contestants(gladiator1, gladiator2));
        }
        return contestants;
    }

    private Gladiator getChampion(Tournament tournament) {
        Contestants contestants = tournament.getContestants();
        Gladiator winner;
        if (contestants != null) {
            winner = simulateCombat(new Combat(contestants));
        } else {
            winner = simulateCombat(new Combat(new Contestants(getChampion(tournament.getLeftBranch()), getChampion(tournament.getRightBranch()))));
        }
        return winner;
    }

    private Gladiator simulateCombat(Combat combat) {
        Gladiator gladiator1 = combat.getGladiator1();
        Gladiator gladiator2 = combat.getGladiator2();
        if (gladiator2 == null) {
            announceNoNeedForCombat(gladiator1);
            gladiator1.levelUp();
            return gladiator1;
        }
        announceCombat(gladiator1, gladiator2);

        Gladiator winner = combat.simulate();
        Gladiator loser;
        if (winner.equals(gladiator1)) {
            loser = gladiator2;
        } else {
            loser = gladiator1;
        }

        displayCombatLog(combat);
        if (sparing) {
            if (RandomUtils.getChance(SPARING_CHANCE)) {
                loser.recuperate();
                survivingGladiators.add(loser);
                announceWinnerAndLoser(winner, loser);
                return winner;
            }
        }
        announceWinnerAndDead(winner, loser);
        return winner;
    }

    private void announceNoNeedForCombat(Gladiator gladiator) {
        view.display(gladiator + " has no opponent and moved to the next round without fight.");
    }


    public void welcome() {
        view.display("Ave Caesar, and welcome to the Colosseum!");
    }

    public void welcomeAndAskForStages() {
        welcome();
        view.display("How many stages of the Tournament do you wish to watch? (1-10)");
        stages = view.getNumberBetween(MIN_TOURNAMENT_STAGES, MAX_TOURNAMENT_STAGES);
    }

    private void introduceGladiators(List<Gladiator> gladiators) {
        view.display(String.format("\nWe have selected Rome's %d finest warriors for today's Tournament!", gladiators.size()));
        for (Gladiator gladiator: gladiators) {
            view.display(String.format(" - %s", gladiator));
        }
        view.display("\n\"Ave Imperator, morituri te salutant!\"");
    }

    private void announceCombat(Gladiator gladiator1, Gladiator gladiator2) {
        view.display(String.format("\nDuel %s versus %s:", gladiator1.getName(), gladiator2.getName()));
        view.display(String.format(" - %s", gladiator1));
        view.display(String.format(" - %s", gladiator2));
        view.display("Begin!");
    }

    private void displayCombatLog(Combat combat) {
        view.display(String.format(" - %s", combat.getCombatLog(", ")));
    }

    private void announceWinnerAndDead(Gladiator winner, Gladiator loser) {
        view.display(String.format("%s has died, %s wins!\n", loser.getFullName(), winner.getFullName()));
    }

    private void announceWinnerAndLoser(Gladiator winner, Gladiator loser) {
        view.display(String.format("%s has lost, but spared, %s wins!\n", loser.getFullName(), winner.getFullName()));
    }

    private void announceChampion(Gladiator champion) {
        if (champion != null) {
            view.display(String.format("\nThe Champion of the Tournament is %s!\n\n\n", champion.getFullName()));
        } else {
            view.display("\nHave mercy, Caesar, the Tournament will start soon!");
        }
    }

    private void announceNoTournament() {
        view.display("There aren't any surviving gladiator from the last tournament, so this tournament is postponed!");
    }

    private void announceSingleContestant(Gladiator gladiator) {
        view.display("The sole survivor from the previous tournament is " + gladiator + " so this tournament is postponed!");
    }

    public List<Gladiator> getSurvivingGladiators() {
        return survivingGladiators;
    }
}
