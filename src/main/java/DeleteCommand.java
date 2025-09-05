public class DeleteCommand extends Command {

    private final String arg;

    public DeleteCommand(String arg) {
        this.arg = arg.trim();
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        if (tasks.isEmpty()) {
            throw new ChittiException("You have no tasks to delete! Add some tasks first.");
        }

        int taskIndex = Integer.parseInt(arg) - 1;

        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new ChittiException("Task " + (taskIndex + 1) + " doesn't exist! You have " + tasks.size() + " tasks.");
        }

        Task removedTask = tasks.remove(taskIndex);
        ui.showLine();
        System.out.println(" Noted. I've removed this task:");
        System.out.println("\t" + removedTask);
        System.out.println("Now you have " + tasks.size() + " task/s in the list.");
        storage.save(tasks.getTasks());
    }
}


