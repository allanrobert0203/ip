package chitti.command;

import chitti.storage.Storage;
import chitti.task.TaskList;
import chitti.ui.Ui;

/**
 * Exits the application after saving.
 */
public class ExitCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        System.out.println("Bye. Hope to see you again soon!");
        ui.showLine();
        storage.save(tasks.getTasks());
        ui.close();
    }

    @Override
    public boolean isExit() { return true; }
}


