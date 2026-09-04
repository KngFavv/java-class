
import java.util.Scanner;
import java.util.Random;

public static void main(String[] args) {

    Random rnd = new Random();

    String [] game = {"rock", "paper", "scissors"};
    int randomIndex = rnd.nextInt(game.length);
    String randomGame = game[randomIndex];

    Scanner scanner = new Scanner(System.in);

    System.out.println(randomGame);




}
