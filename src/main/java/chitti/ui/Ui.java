package chitti.ui;

import java.util.Scanner;

/**
 * Handles all user interaction: printing messages and reading user input.
 */
public class Ui {

    private final Scanner scanner;

    /**
     * Creates a new Ui backed by standard input/output.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prints the welcome banner and usage help.
     */
    public void welcome() {
        System.out.println("Hello! I'm Chitti the robot. Speed 1 terahertz, memory 1 zigabyte.\nWhat can I do for you?");
        System.out.println("(Commands: 'list', 'mark <number>', 'unmark <number>', 'bye', 'todo <description>',\n 'deadline <description> /by <dateOrDateTime>', 'event <description> /from <dateOrDateTime> /to <dateOrDateTime>', 'on <date>', 'delete <number')");
        System.out.println("\nAccepted date formats: yyyy-MM-dd, yyyy-MM-dd HHmm, d/M/yyyy, d/M/yyyy HHmm");
        showLine();
    }

    /**
     * Reads a full line command from the user.
     *
     * @return the raw command string entered by the user
     */
    public String readCommand() {
        return this.scanner.nextLine();
    }

    /**
     * Prints a horizontal divider line.
     */
    public void showLine() {
        System.out.println("---------------------------");
    }

    /**
     * Prints an error message with a standard prefix.
     *
     * @param message error details to show
     */
    public void showError(String message) {
        System.out.println("\tERROR! " + message);
    }

    /**
     * Closes the underlying input scanner.
     */
    public void close() {
        this.scanner.close();
    }
}


