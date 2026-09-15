import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SimpleCalculator cal = new SimpleCalculator();

        while (true) {


            System.out.println("choose an operation +,-,*,/ or exit by entering nothing");
            String scan = scanner.nextLine();


            if (scan.isEmpty()) {
                break;
            }
            if (scan.equals("+")) {
                System.out.println("what is you number");
                String input = scanner.nextLine();
                int num1 = Integer.parseInt(input);

                String input2 = scanner.nextLine();
                int num2 = Integer.parseInt(input2);
                System.out.println(cal.add(num1, num2));
            }
            if (scan.equals("-")) {
                System.out.println("what is you number");
                String input = scanner.nextLine();
                int num1 = Integer.parseInt(input);

                String input2 = scanner.nextLine();
                int num2 = Integer.parseInt(input2);
                System.out.println(cal.sub(num1, num2));
            }
            if (scan.equals("*")) {
                System.out.println("what is you number");
                String input = scanner.nextLine();
                int num1 = Integer.parseInt(input);

                String input2 = scanner.nextLine();
                int num2 = Integer.parseInt(input2);
                System.out.println(cal.multi(num1, num2));
            }
            if (scan.equals("/")) {
                System.out.println("what is you number");
                String input = scanner.nextLine();
                int num1 = Integer.parseInt(input);

                String input2 = scanner.nextLine();
                int num2 = Integer.parseInt(input2);
                System.out.println(cal.div(num1, num2));
            }


        }
    }
}
