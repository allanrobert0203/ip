package chitti.command;

import chitti.exception.ChittiException;
import chitti.storage.Storage;
import chitti.task.TaskList;
import chitti.ui.Ui;

public class MarkCommand extends Command {

    private final String arg;

    public MarkCommand(String arg) {
        this.arg = arg.trim();
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        if (tasks.isEmpty()) {
            throw new ChittiException("You have no tasks to mark! Add some tasks first.");
        }

        int taskIndex = Integer.parseInt(arg) - 1;

        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new ChittiException("chitti.task.Task " + (taskIndex + 1) + " doesn't exist! You have " + tasks.size() + " tasks.");
        }

        if (tasks.get(taskIndex).isMarked()) {
            System.out.println("chitti.task.Task " + (taskIndex + 1) + " is already marked.");
            return;
        }

        tasks.get(taskIndex).markAsDone();
        System.out.println("Great job! I have marked this task as done!");
        System.out.println("\t" + tasks.get(taskIndex).toString());
        storage.save(tasks.getTasks());
    }
}


