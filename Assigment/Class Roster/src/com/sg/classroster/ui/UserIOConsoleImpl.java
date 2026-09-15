package com.sg.classroster.ui;

import java.util.Scanner;

public class UserIOConsoleImpl implements UserIO {

    private Scanner scanner = new Scanner(System.in);

    //shorter  sout
    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    //prints a message that is put in the parameter and then returns a double input from the user
    @Override
    public double readDouble(String prompt) {
        print(prompt);
        return Double.parseDouble(scanner.nextLine());
    }

    //reads the user input and keeping looping until the condition is met and return it as value
    @Override
    public double readDouble(String prompt, double min, double max) {
        double value;

        do {
            value = readDouble(prompt);

            if (value < min || value > max) {
                print("Please enter a value between " + min + " and " + max);
            }

        } while (value < min || value > max);

        return value;
    }

    //prints a message that is put in the parameter and then returns a float input from the user
    @Override
    public float readFloat(String prompt) {
        print(prompt);
        return Float.parseFloat(scanner.nextLine());
    }

    //reads the user input and keeping looping until the condition is met and return it as value
    @Override
    public float readFloat(String prompt, float min, float max) {
        float value;
        min = 1;
        max = 5;
        do {
            value = readFloat(prompt);

            if (value < min || value > max) {
                print("Please enter a value between " + min + " and " + max);
            }

        } while (value < min || value > max);

        return value;
    }


    @Override
    public int readInt(String prompt) {
        print(prompt);
        return Integer.parseInt(scanner.nextLine());
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int value;
        min = 1;
        max = 5;

        do {
            value = readInt(prompt);

            if (value < min || value > max) {
                print("Please enter a value between " + min + " and " + max);
            }

        } while (value < min || value > max);

        return value;
    }

    @Override
    public long readLong(String prompt) {
        print(prompt);
        return Long.parseLong(scanner.nextLine());
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        long value;
        min = 1;
        max = 5;
        do {
            value = readLong(prompt);

            if (value < min || value > max) {
                print("Please enter a value between " + min + " and " + max);
            }

        } while (value < min || value > max);

        return value;
    }

    @Override
    public String readString(String prompt) {
        print(prompt);
        return scanner.nextLine();
    }
}
