package chitti.command;

import chitti.storage.Storage;
import chitti.task.TaskList;
import chitti.ui.Ui;

/**
 * Base type for all commands.
 */
public abstract class Command {

    /** Executes the command. */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws Exception;

    /** Returns true if this command will exit the app after execution. */
    public boolean isExit() {
        return false;
    }
}


