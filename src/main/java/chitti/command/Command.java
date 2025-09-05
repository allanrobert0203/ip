package chitti.command;

import chitti.storage.Storage;
import chitti.task.TaskList;
import chitti.ui.Ui;

public abstract class Command {

    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws Exception;

    public boolean isExit() {
        return false;
    }
}


