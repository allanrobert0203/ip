public class ListCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.isEmpty()) {
            System.out.println("Your task list is empty! You need a well deserved rest!");
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            int order = i + 1;
            System.out.println(order + ". " + tasks.get(i).toString());
        }
    }
}


