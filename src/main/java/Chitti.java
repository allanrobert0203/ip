import java.util.Scanner;

public class Chitti {
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        Task[] list = new Task[100];
        int counter = 0;

        System.out.println("Hello! I'm Chitti the robot. Speed 1 terahertz, memory 1 zigabyte.\nWhat can I do for you?");
        System.out.println("(Commands: 'list', 'mark <number>', 'unmark <number>', 'bye', 'todo', 'deadline <description> /<duedate>', 'event <description> /<from> /<to>')");
        System.out.println("---------------------------");
        String input = myScanner.nextLine();

        while (!input.equals("bye")) {
            if (input.equals("list")) {
                for (int i = 0; i < counter; i++) {
                    int order = i + 1;
                    System.out.println(order + ". " + list[i].toString());
                }
            } else if (input.startsWith("mark ")) {
                String[] parts = input.split(" ");
                int taskIndex = Integer.parseInt(parts[1]) - 1;
                list[taskIndex].markAsDone();

                System.out.println("Great job! I have marked this task as done!");
                System.out.println("\t" + list[taskIndex].toString());
            } else if (input.startsWith("unmark ")) {
                String[] parts = input.split(" ");
                int taskIndex = Integer.parseInt(parts[1]) - 1;
                list[taskIndex].markAsNotDone();

                System.out.println("Awwww, I've marked this task as not done yet:");
                System.out.println("\t" + list[taskIndex]);
            } else if (input.startsWith(("todo "))) {
                String description = input.substring(5);

                int tasks = counter + 1;
                ToDo newToDo = new ToDo(description);
                System.out.println("Got it! I've added this task:");
                System.out.println("\t" + newToDo.toString());
                System.out.println("Now you got " + tasks + " task/s in the list" );
                list[counter] = newToDo;
                counter++;
            } else if (input.startsWith(("deadline "))) {
                String remainingInput = input.substring(9);
                String[] parts = remainingInput.split(" /by ");

                String description = parts[0];
                String due = parts[1];

                int tasks = counter + 1;
                Deadline newDeadline = new Deadline(description, due);
                System.out.println("Got it! I've added this task:");
                System.out.println("\t" + newDeadline.toString());
                System.out.println("Now you got " + tasks + " task/s in the list" );
                list[counter] = newDeadline;
                counter++;
            } else if (input.startsWith("event ")) {
                String restOfInput = input.substring(6);
                String[] parts = restOfInput.split(" /from | /to ");

                String description = parts[0];
                String from = parts[1];
                String to = parts[2];

                int tasks = counter + 1;
                Event newEvent = new Event(description, from, to);
                System.out.println("Got it! I've added this task:");
                System.out.println("\t" + newEvent.toString());
                System.out.println("Now you got " + tasks + " task/s in the list" );
                list[counter] = newEvent;
                counter++;
            }
            System.out.println("---------------------------");
            input = myScanner.nextLine();
        }

        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("---------------------------");
        myScanner.close();

    }
}