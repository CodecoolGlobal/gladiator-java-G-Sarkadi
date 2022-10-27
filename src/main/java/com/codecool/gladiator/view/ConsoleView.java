package com.codecool.gladiator.view;

import java.util.Scanner;

/**
 * Basic console view implementation
 */
public class ConsoleView implements Viewable {

    @Override
    public void display(String text) {
        System.out.println(text);
    }

    @Override
    public int getNumberBetween(int min, int max) {
        int number = - 1;
        String text = "Please give me a number between " + min + " and " + max;
        Scanner in = new Scanner(System.in);
        while (number < min || number > max) {
            display(text);
            String input = in.nextLine();
            try {
                number = Integer.parseInt(input);
            } catch (NumberFormatException e) {
            }
        }
        return number;
    }
}
