import java.util.Scanner;

public class Chitti {
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        Task[] list = new Task[100];
        int counter = 0;

        System.out.println("Hello! I'm Chitti the robot. Speed 1 terahertz, memory 1 zigabyte.\nWhat can I do for you?");
        System.out.println("(Commands: 'list', 'mark <number>', 'unmark <number>', 'bye')");
        System.out.println("---------------------------");
        String echo = myScanner.nextLine();

        while (!echo.equals("bye")) {
            if (echo.equals("list")) {
                for (int i = 0; i < counter; i++) {
                    int order = i + 1;
                    System.out.println(order + ". " + list[i].toString());
                }
            } else if (echo.startsWith("mark ")) {
                String[] parts = echo.split(" ");
                int taskIndex = Integer.parseInt(parts[1]) - 1;
                list[taskIndex].markAsDone();

                System.out.println("Great job! I have marked this task as done!");
                System.out.println("\t" + list[taskIndex]);
            } else if (echo.startsWith("unmark ")) {
                String[] parts = echo.split(" ");
                int taskIndex = Integer.parseInt(parts[1]) - 1;
                list[taskIndex].markAsNotDone();

                System.out.println("Awwww, I've marked this task as not done yet:");
                System.out.println("\t" + list[taskIndex]);
            } else {
                System.out.println("Added: " + echo);
                list[counter] = new Task(echo);
                counter++;
            }
            System.out.println("---------------------------");
            echo = myScanner.nextLine();
        }

        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("---------------------------");
        myScanner.close();

    }
}