import java.util.Scanner;

public class Chitti {


    private static void printError(String message) {
        System.out.println("\tERROR! " + message);
    }

    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        Task[] list = new Task[100];
        int counter = 0;

        System.out.println("Hello! I'm Chitti the robot. Speed 1 terahertz, memory 1 zigabyte.\nWhat can I do for you?");
        System.out.println("(Commands: 'list', 'mark <number>', 'unmark <number>', 'bye', 'todo <description>', 'deadline <description> /by <duedate>', 'event <description> /from <time> /to <time>')");
        System.out.println("---------------------------");
        String input = myScanner.nextLine();

        while (!input.equals("bye")) {
            try {
                if (input.equals("list")) {
                    if (counter == 0) {
                        System.out.println("Your task list is empty! You need a well deserved rest!"); // if the list is empty
                    } else {
                        for (int i = 0; i < counter; i++) {
                            int order = i + 1;
                            System.out.println(order + ". " + list[i].toString());
                        }
                    }
                } else if (input.startsWith("mark ")) {
                    if (counter == 0) {
                        throw new ChittiException("You have no tasks to mark! Add some tasks first."); // When trying to mark an empty list
                    }

                    String[] parts = input.split(" ");
                    if (parts.length < 2) {
                        throw new ChittiException("Please specify a task number. Use the following format: mark <number>"); // Using the 'mark' function without specifying the task number
                    }

                    int taskIndex = Integer.parseInt(parts[1]) - 1;
                    if (taskIndex < 0 || taskIndex >= counter) {
                        throw new ChittiException("Task " + (taskIndex + 1) + " doesn't exist! You have " + counter + " tasks."); // when trying to mark a task number that is outside the list range
                    }

                    if (list[taskIndex].isMarked()) {
                        System.out.println("Task " + (taskIndex + 1) + " is already marked.");
                    } else {
                        list[taskIndex].markAsDone();
                        System.out.println("Great job! I have marked this task as done!");
                        System.out.println("\t" + list[taskIndex].toString());
                    }

                } else if (input.startsWith("unmark ")) {
                    if (counter == 0) {
                        throw new ChittiException("You have no tasks to unmark! Add some tasks first."); // when trying to unmark an empty list
                    }

                    String[] parts = input.split(" ");
                    if (parts.length < 2) {
                        throw new ChittiException("Please specify a task number. Use the following format: unmark <number>"); // Using the 'unmark' function without specifying the task number
                    }

                    int taskIndex = Integer.parseInt(parts[1]) - 1;
                    if (taskIndex < 0 || taskIndex >= counter) {
                        throw new ChittiException("Task " + (taskIndex + 1) + " doesn't exist! You have " + counter + " tasks."); // when trying to unmark a task number that is outside the list range
                    }

                    if (!list[taskIndex].isMarked()) {
                        System.out.println("Task " + (taskIndex + 1) + " is already unmarked."); 
                    } else {
                        list[taskIndex].markAsNotDone();
                        System.out.println("Awwww, I've marked this task as not done yet:");
                        System.out.println("\t" + list[taskIndex]);
                    }

                } else if (input.equals("todo")) {
                    throw new ChittiException("The description of a todo cannot be empty. Use the following format: todo <description>"); // when trying to use 'todo' without any task description

                } else if (input.startsWith("todo ")) {
                    String description = input.substring(5).trim();
                    if (description.isEmpty()) {
                        throw new Exception("The description of a todo cannot be empty. Use the following format: todo <description>"); // when trying to use 'todo' with a space but without any task description
                    }

                    ToDo newToDo = new ToDo(description);
                    list[counter] = newToDo;
                    counter++;

                    System.out.println("Got it! I've added this task:");
                    System.out.println("\t" + newToDo.toString());
                    System.out.println("Now you have " + counter + " task(s) in the list");

                } else if (input.equals("deadline")) {
                    throw new ChittiException("The description of a deadline cannot be empty. Use the following format: deadline <description> /by <duedate>"); // when trying to use 'deadline' without any task description

                } else if (input.startsWith("deadline ")) {
                    String remainingInput = input.substring(9).trim();
                    if (!remainingInput.contains(" /by ")) {
                        throw new ChittiException("Missing '/by' keyword. Use the following format: deadline <description> /by <duedate>"); // when user forgets to use the '/by'
                    }

                    String[] parts = remainingInput.split(" /by ");
                    if (parts.length < 2) {
                        throw new ChittiException("Invalid format. Use the following format: deadline <description> /by <duedate>"); // invalid formatting
                    }

                    String description = parts[0].trim();
                    String due = parts[1].trim();

                    if (description.isEmpty()) {
                        throw new ChittiException("The description of a deadline cannot be empty."); // when description is missing
                    }
                    if (due.isEmpty()) {
                        throw new ChittiException("The due date cannot be empty. Use the following format: deadline <description> /by <duedate>"); // when duedate is missing
                    }

                    Deadline newDeadline = new Deadline(description, due);
                    list[counter] = newDeadline;
                    counter++;

                    System.out.println("Got it! I've added this task:");
                    System.out.println("\t" + newDeadline.toString());
                    System.out.println("Now you have " + counter + " task(s) in the list");

                } else if (input.equals("event")) {
                    throw new ChittiException("The description of an event cannot be empty. Use the following format: event <description> /from <time> /to <time>"); // when description is missing

                } else if (input.startsWith("event ")) {
                    String restOfInput = input.substring(6).trim();
                    if (!restOfInput.contains(" /from ") || !restOfInput.contains(" /to ")) {
                        throw new ChittiException("Missing '/from' or '/to' keyword. Use the following format: event <description> /from <time> /to <time>"); // when the '/from' or '/to' keyword is missing
                    }

                    String[] parts = restOfInput.split(" /from | /to ");
                    if (parts.length < 3) {
                        throw new ChittiException("Invalid format. Use the following format: event <description> /from <time> /to <time>"); // invalid formatting
                    }

                    String description = parts[0].trim();
                    String from = parts[1].trim();
                    String to = parts[2].trim();

                    if (description.isEmpty()) {
                        throw new ChittiException("The description of an event cannot be empty."); // when description is missing
                    }
                    if (from.isEmpty() || to.isEmpty()) {
                        throw new ChittiException("Start and end times cannot be empty. Usage: event <description> /from <time> /to <time>"); // when start or end time is missing
                    }

                    Event newEvent = new Event(description, from, to);
                    list[counter] = newEvent;
                    counter++;

                    System.out.println("Got it! I've added this task:");
                    System.out.println("\t" + newEvent.toString());
                    System.out.println("Now you have " + counter + " task(s) in the list");

                } else {
                    throw new ChittiException("I'm sorry, but I don't know what that means 😭"); // invalid command
                }

            } catch (NumberFormatException e) {
                printError("Please enter a valid number for the task.");
            } catch (ChittiException e) {
                printError(e.getMessage());
            } catch (ArrayIndexOutOfBoundsException e) {
                printError("I've run out of memory! Too many tasks!"); // when more than 100 tasks
            } catch (Exception e) {
                printError("Oops! Something unexpected went wrong. Please try again.");
                System.out.println("---------------------------");
                System.out.println("FOR DEBUGGING PURPOSE");
                e.printStackTrace();
                System.out.println("---------------------------");
            }

            System.out.println("---------------------------");
            input = myScanner.nextLine();
        }

        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("---------------------------");
        myScanner.close();
    }
}