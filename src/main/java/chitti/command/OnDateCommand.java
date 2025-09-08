package chitti.command;

import chitti.exception.ChittiException;
import chitti.storage.Storage;
import chitti.task.Deadline;
import chitti.task.Event;
import chitti.task.Task;
import chitti.task.TaskList;
import chitti.ui.Ui;
import chitti.util.DateTimeUtil;

public class OnDateCommand extends Command {

    private final String dateStr;

    public OnDateCommand(String dateStr) {
        this.dateStr = dateStr.trim();
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        DateTimeUtil.ParsedDateTime parsed = DateTimeUtil.tryParse(dateStr);

        if (parsed == null) {
            throw new ChittiException("Could not understand the date. Try formats like yyyy-MM-dd or d/M/yyyy");
        }

        System.out.println("Tasks on " + DateTimeUtil.formatForDisplay(parsed.dateTime, false) + ":");
        boolean any = false;

        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);

            if (t instanceof Deadline) {
                Deadline deadline = (Deadline) t;
                if (deadline.getDateTime().toLocalDate().equals(parsed.dateTime.toLocalDate())) {
                    int order = i + 1;
                    System.out.println(order + ". " + deadline.toString());
                    any = true;
                }
            } else if (t instanceof Event) {
                Event event = (Event) t;
                if (event.getStartDateTime().toLocalDate().equals(parsed.dateTime.toLocalDate()) || event.getEndDateTime().toLocalDate().equals(parsed.dateTime.toLocalDate())) {
                    int order = i + 1;
                    System.out.println(order + ". " + event.toString());
                    any = true;
                }
            }
        }
        if (!any) {
            System.out.println("No tasks found on this date.");
        }
    }
}


