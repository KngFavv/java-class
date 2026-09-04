package main;

import java.util.Random;
import java.util.Scanner;

public class DoOrDont {

    public static void main(String[] args) {
        System.out.println("I've chosen a number between -100 and 100. Betcha can't guess it!");
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        int rand = random.nextInt(100) + -100;
        System.out.println(rand);


        while (true) {
            int counter = 0;
            String num = scanner.nextLine();
            int num1 = Integer.parseInt(num);


            if (num1 == rand && counter == 1){
                System.out.println("Wow, nice guess! That was it!");
                break;
            } else if (num1 == rand) {
                System.out.println("Finally! It's about time you got it!");
                break;

            }

            if(num1 != rand && num1 < rand){
                System.out.println("Ha, nice try - too low! Try again!");
            }if (num1 != rand && num1 > rand){
                System.out.println("Too bad, way too high. Try again!");
            }
            counter++;

        }


    }
}
