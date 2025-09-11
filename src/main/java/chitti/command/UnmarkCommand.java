package chitti.command;

import chitti.exception.ChittiException;
import chitti.storage.Storage;
import chitti.task.TaskList;
import chitti.ui.Ui;

/**
 * Marks a task as not done by index (1-based).
 */
public class UnmarkCommand extends Command {
    private final String arg;

    public UnmarkCommand(String arg) {
        this.arg = arg.trim();
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        if (tasks.isEmpty()) {
            throw new ChittiException("You have no tasks to unmark! Add some tasks first.");
        }

        int taskIndex = Integer.parseInt(arg) - 1;

        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new ChittiException("chitti.task.Task "
                    + (taskIndex + 1)
                    + " doesn't exist! You have "
                    + tasks.size() + " tasks.");
        }

        if (!tasks.get(taskIndex).isMarked()) {
            System.out.println("chitti.task.Task " + (taskIndex + 1) + " is already unmarked.");
            return;
        }

        tasks.get(taskIndex).markAsNotDone();
        System.out.println("Awwww, I've marked this task as not done yet:");
        System.out.println("\t" + tasks.get(taskIndex));
        storage.save(tasks.getTasks());
    }
}


