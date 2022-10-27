package com.codecool.gladiator.view;

/**
 * Interface used for basic View functionality (displaying text)
 */
public interface Viewable {
    /**
     * Displays given text into the View
     *
     * @param text the text to be displayed
     */
    void display(String text);

    /**
     * Asks the user for a number between min and max and returns it
     * @param min minimal value of the number accepted
     * @param max maximal value of the number accepted
     * @return the number from the user
     */
    int getNumberBetween(int min, int max);
}
