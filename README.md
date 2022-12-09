# Gladiator tournament game

Organize a gladiator tournament for life and death. This is a small Java program to simulate a gladiator tournament and demonstrate some OOP principles.
It uses the MVC pattern and generates the tournament pairing with a tree.

## Model
Contains an abstract Gladiator class, from whom all of the different gladiators inherit. Also has a GladiatorFactory for gladiator creation an a model for the
pairs of contestants.

## View
So far, it only has a console view, which implements a View interface. If there will be a proper display, this will make the switch easier.
It gets the user's input and shows the results of the tournament.

## Controller
It handles the business logic needed to organize a tournament, create gladiators, and simulate the fights.
Uses utility classes to generate random numbers and a tree to generate the tournament pairings.

## The game

### Start
The user is asked for a starting number between 1 and 10. The number of gladiators will be 2 to the power of this number.
The game creates the necessary number of gladiators and puts them in a tournament tree. It announces the gladiators with their stats.

### Fight
The fight between two contestants is round-based.
The attacker has a chance to hit the target, and if there is a hit, the damage is calculated according to the gladiators' stats, level, and random multipliers.
The game broadcasts and comments on every round between the fighters.
The fight will end if one of the gladiators loses all his HP. The winner is healed up, gets a level up, and plays with the winner of the neighboring branch.
The loser has a 25% chance to survive. If he survives, he also heals up and waits for the end of the tournament.

### Results
Eventually, all the fights will be over.
The game announces the winner of the tournament and starts a second tournament between the survivors of the first one.
This time the fight goes to the death, no gladiator can be spared.
If there are fewer than 2 survivors from the first tournament, the second one is postponed.
