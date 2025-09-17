package chitti.command;

import java.util.List;
import java.util.stream.Collectors;

import chitti.storage.Storage;
import chitti.task.Task;
import chitti.task.TaskList;
import chitti.ui.Ui;


/**
 * Finds any duplicates that were added to your list
 */
public class FindDuplicatesCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        List<Task> duplicateTasks = findDuplicates(tasks);

        if (duplicateTasks.isEmpty()) {
            System.out.println("Great! No duplicate tasks found in your list.");
        } else {
            System.out.println("Here are the duplicate tasks in your list:");
            for (int i = 0; i < duplicateTasks.size(); i++) {
                System.out.println((i + 1) + ". " + duplicateTasks.get(i));
            }
        }
    }

    private List<Task> findDuplicates(TaskList tasks) {
        return tasks.getTasks().stream()
                .filter(task -> tasks.countDuplicates(task) > 1)
                .distinct()
                .collect(Collectors.toList());
    }
}
