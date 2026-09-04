package main;

import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors {

    public static void main(String[] args) {

        while(true){
            
            int num = userInput();
            theGame(num);

            System.out.println("Do you want to play again? type Y/N: ");
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();

            if(input.equals("N")){
                System.out.println("Thanks for playing!");
                break;
            }
            if(input.equals("Y")){
                continue;
            }
        }


    }

    public static int userInput() {
        System.out.println("How many rounds do you want to play From 1 to 10 ?: ");
        Scanner scanner = new Scanner(System.in);
        String round = scanner.nextLine();
        int round1 = Integer.parseInt(round);

        if (round1 < 1 || round1 > 10 ) {
            System.out.println("You did not enter a valid number");
            return 0;

        }else {
            return round1;
        }
    }

    public static void theGame(int num){
        String[] choice = {"rock", "paper", "scissors"};

        int tie = 0;
        int userWin = 0;
        int compWin = 0;



        for(int i = 0; i < num; i++){
            System.out.println("What is your choice: rock, paper, scissors");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            Random random = new Random();
            int rand = random.nextInt(choice.length);
            String randomChoice = choice[rand];


            if(input.equals(randomChoice)){
                System.out.println("Tie");

                tie++;

            }else if(input.equals("rock") && randomChoice.equals("paper")){
                System.out.println("Computer wins!");
                compWin++;

            }else if (input.equals("rock") && randomChoice.equals("scissors")) {
                System.out.println("User wins!");
                userWin++;

            }else if (input.equals("paper") && randomChoice.equals("rock")) {
                System.out.println("User wins!");
                userWin++;

            }else if (input.equals("paper") && randomChoice.equals("scissors")) {
                System.out.println("Computer wins!");
                compWin++;

            }else if (input.equals("scissors") && randomChoice.equals("paper")) {
                System.out.println("User wins!");
                userWin++;

            }else if (input.equals("scissors") && randomChoice.equals("rock")) {
                System.out.println("Computer wins!");
                compWin++;
            }

        }

        System.out.println("Number of Ties: " + tie);
        System.out.println("Number of Computer Win: " + compWin);
        System.out.println("Number of User Win: " + userWin);

        if(compWin > userWin){
            System.out.println("Computer Wins Overall!");
        }else if(userWin == compWin) {
            System.out.println("Its A Draw Overall!");
        }else{
            System.out.println("User Wins Overall!");
        }






    }
}


