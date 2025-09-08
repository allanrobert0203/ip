package chitti.command;

import chitti.storage.Storage;
import chitti.task.TaskList;
import chitti.ui.Ui;

/**
 * Prints all tasks with their status.
 */
public class ListCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.isEmpty()) {
            System.out.println("Your task list is empty! You need a well deserved rest!");
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            int order = i + 1;
            System.out.println(order + ". " + tasks.get(i).toString());
        }
    }
}


