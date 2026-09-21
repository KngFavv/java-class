package com.flooring.view;

import java.util.Scanner;

public class UserIOConsoleImpl implements UserIO {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    @Override
    public String readString(String prompt) {
        print(prompt);
        return scanner.nextLine();
    }

    @Override
    public int readInt(String prompt) {
        while (true) {
            try {
                print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                print("Invalid input. Please enter a number.");
            }
        }
    }
}
