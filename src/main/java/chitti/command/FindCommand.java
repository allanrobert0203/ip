package chitti.command;

import chitti.exception.ChittiException;
import chitti.storage.Storage;
import chitti.task.Task;
import chitti.task.TaskList;
import chitti.ui.Ui;

/**
 * Finds tasks that contain the given keyword in their description.
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Constructs a FindCommand with the given search keyword.
     *
     * @param keyword the keyword to search for in task descriptions
     */
    public FindCommand(String keyword) {
        this.keyword = keyword.trim();
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ChittiException {
        if (keyword.isEmpty()) {
            throw new ChittiException("Please provide a keyword to search for. Use: find <keyword>");
        }

        TaskList matchingTasks = new TaskList();

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }

        if (matchingTasks.isEmpty()) {
            throw new ChittiException("No tasks found containing: " + keyword);
        }

        ui.showLine();
        System.out.println(" Here are the matching tasks in your list:");
        for (int i = 0; i < matchingTasks.size(); i++) {
            System.out.println(" " + (i + 1) + "." + matchingTasks.get(i).toString());
        }
        ui.showLine();
    }
}
