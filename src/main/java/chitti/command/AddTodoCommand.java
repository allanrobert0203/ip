package chitti.command;

import chitti.exception.ChittiException;
import chitti.storage.Storage;
import chitti.task.TaskList;
import chitti.task.ToDo;
import chitti.ui.Ui;

/**
 * Adds a new ToDo task.
 */
public class AddTodoCommand extends Command {
    private final String description;

    public AddTodoCommand(String description) {
        this.description = description.trim();
    }

    @Override

    public void execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        if (description.isEmpty()) {
            throw new ChittiException("The description of a todo cannot be empty. Use the following format: todo <description>");
        }

        ToDo newToDo = new ToDo(description);
        tasks.add(newToDo);
        System.out.println("Got it! I've added this task:");
        System.out.println("\t" + newToDo.toString());
        System.out.println("Now you have " + tasks.size() + " task(s) in the list");
        storage.save(tasks.getTasks());
    }
}


