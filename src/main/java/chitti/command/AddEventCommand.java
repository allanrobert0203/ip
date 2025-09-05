package chitti.command;

import chitti.exception.ChittiException;
import chitti.storage.Storage;
import chitti.task.Event;
import chitti.task.TaskList;
import chitti.ui.Ui;

public class AddEventCommand extends Command {

    private final String rest;

    public AddEventCommand(String rest) {
        this.rest = rest.trim();
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        if (!rest.contains(" /from ") || !rest.contains(" /to ")) {
            throw new ChittiException("Missing '/from' or '/to' keyword. Use the following format: event <description> /from <time> /to <time>");
        }

        String[] parts = rest.split(" /from | /to ");

        if (parts.length < 3) {
            throw new ChittiException("Invalid format. Use the following format: event <description> /from <time> /to <time>");
        }

        String description = parts[0].trim();
        String from = parts[1].trim();
        String to = parts[2].trim();

        if (description.isEmpty()) {
            throw new ChittiException("The description of an event cannot be empty.");
        }

        if (from.isEmpty() || to.isEmpty()) {
            throw new ChittiException("Start and end times cannot be empty. Usage: event <description> /from <time> /to <time>");
        }

        Event newEvent = new Event(description, from, to);
        tasks.add(newEvent);
        System.out.println("Got it! I've added this task:");
        System.out.println("\t" + newEvent.toString());
        System.out.println("Now you have " + tasks.size() + " task(s) in the list");
        storage.save(tasks.getTasks());
    }
}


