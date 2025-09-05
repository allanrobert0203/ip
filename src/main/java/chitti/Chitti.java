package chitti;

import chitti.command.Command;
import chitti.command.Parser;
import chitti.exception.ChittiException;
import chitti.storage.Storage;
import chitti.task.TaskList;
import chitti.ui.Ui;

public class Chitti {

	private final Storage storage;
	private TaskList tasks;
	private final Ui ui;

	public Chitti(String filePath) {
		this.ui = new Ui();
		this.storage = new Storage(filePath);
		try {
			this.tasks = new TaskList(this.storage.load());
		} catch (Exception e) {
			this.ui.showError("Failed to load tasks from file. Starting with an empty list.");
			this.tasks = new TaskList();
		}
	}

	public void run() {
		ui.welcome();
		boolean isExit = false;
		while (!isExit) {
			try {
				String fullCommand = ui.readCommand();
				ui.showLine();
				Command c = Parser.parse(fullCommand);
				c.execute(tasks, ui, storage);
				isExit = c.isExit();
			} catch (ChittiException e) {
				ui.showError(e.getMessage());
			} catch (Exception e) {
				ui.showError("Oops! Something unexpected went wrong. Please try again.");
			}
			ui.showLine();
		}
	}

	public static void main(String[] args) {
		new Chitti("./data/chitti.txt").run();
	}
}