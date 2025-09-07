package chitti.ui;

import java.util.Scanner;

public class Ui {

    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void welcome() {
        System.out.println("Hello! I'm Chitti the robot. Speed 1 terahertz, memory 1 zigabyte.\nWhat can I do for you?");
        System.out.println("(Commands: 'list', 'mark <number>', 'unmark <number>', 'bye', 'todo <description>',\n 'deadline <description> /by <dateOrDateTime>', 'event <description> /from <dateOrDateTime> /to <dateOrDateTime>', 'on <date>', 'delete <number')");
        System.out.println("\nAccepted date formats: yyyy-MM-dd, yyyy-MM-dd HHmm, d/M/yyyy, d/M/yyyy HHmm");
        showLine();
    }

    public String readCommand() {
        return this.scanner.nextLine();
    }

    public void showLine() {
        System.out.println("---------------------------");
    }

    public void showError(String message) {
        System.out.println("\tERROR! " + message);
    }

    public void close() {
        this.scanner.close();
    }
}


