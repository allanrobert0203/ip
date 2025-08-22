import java.util.Scanner;

public class Chitti {
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        String[] list = new String[100];
        int counter = 0;

        System.out.println("Hello! I'm Chitti \nWhat can I do for you?");
        System.out.println("---------------------------");
        String echo = myScanner.nextLine();

        while (!echo.equals("bye") ) {
            if (!echo.equals("list")) {
                System.out.println("Added: " + echo);
                System.out.println("---------------------------");
                list[counter] = echo;
                counter++;
                echo = myScanner.nextLine();
            } else {
                for (int i = 0; i < counter; i++) {
                    int order = i + 1;
                    System.out.println(order + ". " + list[i]);
                }
                System.out.println("---------------------------");
                echo = myScanner.nextLine();
            }
        }

        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("---------------------------");
        myScanner.close();

    }
}
