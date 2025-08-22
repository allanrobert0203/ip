import java.util.Scanner;

public class Chitti {
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);

        System.out.println("Hello! I'm Chitti \nWhat can I do for you?");
        System.out.println("---------------------------");
        String echo = myScanner.nextLine();

        while (!echo.equals("bye")) {
            System.out.println(echo);
            System.out.println("---------------------------");
            echo = myScanner.nextLine();
        }

        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("---------------------------");
        myScanner.close();

    }
}
