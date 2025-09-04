import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Storage {

	private final File file;

	public Storage(String relativePath) {
        this.file = new File(relativePath);
	}

	public ArrayList<Task> load() throws IOException {
		ensureFileExists();
		ArrayList<Task> tasks = new ArrayList<>();
		try (Scanner scanner = new Scanner(this.file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine().trim();
				if (line.isEmpty()) {
					continue;
				}
				Task task = parseLineToTask(line);
				if (task != null) {
					tasks.add(task);
				}
			}
		}
		return tasks;
	}

	public void save(List<Task> tasks) throws IOException {
		ensureFileExists();
		try (FileWriter writer = new FileWriter(this.file)) {
			for (Task task : tasks) {
				writer.write(serializeTask(task));
				writer.write(System.lineSeparator());
			}
		}
	}

	private void ensureFileExists() throws IOException {
		File parent = this.file.getParentFile();
		if (parent != null && !parent.exists()) {
			parent.mkdirs();
		}
		if (!this.file.exists()) {
			this.file.createNewFile();
		}
	}

	private String serializeTask(Task task) {
		String status = task.isMarked() ? "1" : "0";
		if (task instanceof ToDo) {
			return String.join(" | ", "T", status, task.description);
		} else if (task instanceof Deadline) {
			Deadline d = (Deadline) task;
			return String.join(" | ", "D", status, d.description, d.date);
		} else if (task instanceof Event) {
			Event e = (Event) task;
			return String.join(" | ", "E", status, e.description, e.startDateTime, e.endDateTime);
		} else {
			return String.join(" | ", "T", status, task.description);
		}
	}

	private Task parseLineToTask(String line) {
		String[] parts = line.split("\\s*\\|\\s*");
		if (parts.length < 3) {
			return null;
		}
		String type = parts[0];
		boolean done = "1".equals(parts[1]);
		String description = parts[2];
		Task task;
		switch (type) {
			case "T":
				task = new ToDo(description);
				break;
			case "D":
				if (parts.length < 4) {
					return null;
				}
				task = new Deadline(description, parts[3]);
				break;
			case "E":
				if (parts.length < 5) {
					return null;
				}
				task = new Event(description, parts[3], parts[4]);
				break;
			default:
				return null;
		}
		if (done) {
			task.markAsDone();
		}
		return task;
	}
}


