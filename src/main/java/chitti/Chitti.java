package chitti;

import chitti.command.Command;
import chitti.command.Parser;
import chitti.exception.ChittiException;
import chitti.storage.Storage;
import chitti.task.TaskList;
import chitti.ui.Ui;

/**
 * Main application class for Chitti the robot task manager.
 * This class serves as the central component that coordinates between
 * the user interface, task storage, and command execution.
 * It initializes the application components and manages the main execution loop.
 */
public class Chitti {

    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    /**
     * Constructs a new Chitti application instance with the specified storage file path.
     * Initializes the user interface, storage system, and loads existing tasks from storage.
     * If loading fails, starts with an empty task list and displays an error message.
     *
     * @param filePath the relative path to the storage file where tasks are persisted
     */
    public Chitti(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(this.storage.load());
        } catch (Exception e) {
            this.ui.showError("Failed to load tasks from file. Starting with an empty list.");
            this.tasks = new TaskList();
        }

        assert this.tasks != null : "TaskList must be properly initialized";
    }

    /**
     * Starts and runs the main application loop for Chitti.
     * Displays a welcome message and continuously processes user commands
     * until an exit command is received. Each command is parsed and executed,
     * with appropriate error handling for any exceptions that may occur.
     * The loop continues until a command that signals exit is executed.
     *
     * The execution flow:
     * 1. Display welcome message
     * 2. Read user input
     * 3. Parse input into a command
     * 4. Execute the command
     * 5. Handle any errors or exceptions
     * 6. Repeat until exit command is received
     */
    public void run() {
        ui.welcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();

                Command c = Parser.parse(fullCommand);
                assert c != null : "Parsed command should not be null";

                c.execute(tasks, ui, storage);
                isExit = c.isExit();

            } catch (ChittiException e) {
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError("Oops! Something unexpected went wrong. Please try again.");
            }
            ui.showLine();
        }
    }
}
