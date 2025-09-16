package chitti.command;

import chitti.exception.ChittiException;
import chitti.storage.Storage;
import chitti.task.Deadline;
import chitti.task.Event;
import chitti.task.Task;
import chitti.task.TaskList;
import chitti.ui.Ui;
import chitti.util.DateTimeUtil;

import java.time.LocalDate;

/**
 * Lists all tasks that fall on a given date.
 */
public class OnDateCommand extends Command {

    private final String dateStr;

    public OnDateCommand(String dateStr) {
        this.dateStr = dateStr.trim();
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        DateTimeUtil.ParsedDateTime parsed = DateTimeUtil.tryParse(dateStr);

        if (parsed == null) {
            throw new ChittiException("Could not understand the date."
                    + "Try formats like yyyy-MM-dd or d/M/yyyy");
        }

        LocalDate targetDate = parsed.dateTime.toLocalDate();
        System.out.println("Tasks on " + DateTimeUtil.formatForDisplay(parsed.dateTime, false) + ":");

        boolean hasMatchingTasks = findAndDisplayMatchingTasks(tasks, targetDate);

        if (!hasMatchingTasks) {
            System.out.println("No tasks found on this date.");
        }
    }

    /**
     * Finds and displays tasks that occur on the target date.
     *
     * @param tasks the task list to search through
     * @param targetDate the date to match tasks against
     * @return true if any matching tasks were found, false otherwise
     */
    private boolean findAndDisplayMatchingTasks(TaskList tasks, LocalDate targetDate) {
        boolean foundAny = false;

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);

            if (isTaskOnDate(task, targetDate)) {
                int displayNumber = i + 1;
                System.out.println(displayNumber + ". " + task.toString());
                foundAny = true;
            }
        }

        return foundAny;
    }

    /**
     * Checks if a task occurs on the specified date.
     *
     * @param task the task to check
     * @param targetDate the date to check against
     * @return true if the task occurs on the target date, false otherwise
     */
    private boolean isTaskOnDate(Task task, LocalDate targetDate) {
        if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            return deadline.getDateTime().toLocalDate().equals(targetDate);
        } else if (task instanceof Event) {
            Event event = (Event) task;
            return event.getStartDateTime().toLocalDate().equals(targetDate)
                    || event.getEndDateTime().toLocalDate().equals(targetDate);
        }
        return false;
    }
}
