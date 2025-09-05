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


