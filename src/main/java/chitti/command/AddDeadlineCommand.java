package chitti.command;

import chitti.exception.ChittiException;
import chitti.storage.Storage;
import chitti.task.Deadline;
import chitti.task.TaskList;
import chitti.ui.Ui;

/**
 * Adds a new Deadline task parsed from "&lt;desc&gt; /by &lt;date&gt;".
 */
public class AddDeadlineCommand extends Command {

    private final String rest;

    public AddDeadlineCommand(String rest) {
        this.rest = rest.trim();
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        if (!rest.contains(" /by ")) {
            throw new ChittiException("Missing '/by' keyword. "
                    + "Use the following format: deadline <description> /by <duedate>");
        }

        String[] parts = rest.split(" /by ");

        if (parts.length < 2) {
            throw new ChittiException("Invalid format. "
                    + "Use the following format: deadline <description> /by <duedate>");
        }

        String description = parts[0].trim();
        String due = parts[1].trim();

        if (description.isEmpty()) {
            throw new ChittiException("The description of a deadline cannot be empty.");
        }

        if (due.isEmpty()) {
            throw new ChittiException("The due date cannot be empty. "
                    + "Use the following format: deadline <description> /by <duedate>");
        }

        Deadline newDeadline = new Deadline(description, due);
        tasks.add(newDeadline);
        System.out.println("Got it! I've added this task:");
        System.out.println("\t" + newDeadline.toString());
        System.out.println("Now you have " + tasks.size() + " task(s) in the list");
        storage.save(tasks.getTasks());
    }
}
