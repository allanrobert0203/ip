package chitti.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import chitti.task.Deadline;
import chitti.task.Event;
import chitti.task.Task;
import chitti.task.ToDo;
import chitti.util.DateTimeUtil;

/**
 * Persists tasks to a file and loads them back, supporting both legacy and
 * new date/time formats.
 */
public class Storage {

    private final File file;

    /**
     * Creates a storage manager pointing at the given file path.
     * @param relativePath path to the data file (relative or absolute)
     */
    public Storage(String relativePath) {
        this.file = new File(relativePath);
    }

    /**
     * Loads tasks from the configured file.
     * @return list of tasks loaded from disk
     * @throws IOException if file IO fails
     */
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
<<<<<<< Updated upstream
=======

        assert tasks != null : "Loaded task list should never be null";
>>>>>>> Stashed changes
        return tasks;
    }

    /**
     * Saves the provided tasks to the configured file, overwriting existing content.
     * @param tasks tasks to persist
     * @throws IOException if file IO fails
     */
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
<<<<<<< Updated upstream
=======
        assert task != null : "Task to serialize must not be null";

>>>>>>> Stashed changes
        String status = task.isMarked() ? "1" : "0";

        if (task instanceof ToDo) {
            return String.join(" | ", "T", status, task.getDescription());
        } else if (task instanceof Deadline) {
            Deadline d = (Deadline) task;
            String stored = DateTimeUtil.formatForStorage(d.getDateTime(), d.hasTimeComponent());
            String hasTimeFlag = d.hasTimeComponent() ? "1" : "0";
            return String.join(" | ", "D", status, d.getDescription(), stored, hasTimeFlag);
        } else if (task instanceof Event) {
            Event e = (Event) task;
            String startStored = DateTimeUtil.formatForStorage(e.getStartDateTime(), e.hasStartTime());
            String endStored = DateTimeUtil.formatForStorage(e.getEndDateTime(), e.hasEndTime());
            String startFlag = e.hasStartTime() ? "1" : "0";
            String endFlag = e.hasEndTime() ? "1" : "0";
            return String.join(" | ", "E", status, e.getDescription(), startStored, startFlag, endStored, endFlag);
        } else {
            return String.join(" | ", "T", status, task.getDescription());
        }
    }

    private Task parseLineToTask(String line) {
<<<<<<< Updated upstream
=======
        assert line != null : "Input line for parsing must not be null";

>>>>>>> Stashed changes
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

            // New format: D | status | desc | storedDateOrDateTime | hasTimeFlag
            if (parts.length >= 5) {
                String stored = parts[3];
                String flag = parts[4];
                DateTimeUtil.ParsedDateTime parsed = DateTimeUtil.tryParse(stored);

                if (parsed != null) {
                    boolean hasTime = "1".equals(flag) || parsed.hasTime;
                    task = new Deadline(description, parsed.dateTime, hasTime);
                } else {
                    task = new Deadline(description, parts[3]);
                }
            } else {
                // Legacy format: D | status | desc | dateString
                task = new Deadline(description, parts[3]);
            }
            break;

        case "E":
            if (parts.length < 5) {
                return null;
            }

            // New format: E | status | desc | startStored | startFlag | endStored | endFlag
            if (parts.length >= 7) {
                String startStored = parts[3];
                String startFlag = parts[4];
                String endStored = parts[5];
                String endFlag = parts[6];
                DateTimeUtil.ParsedDateTime ps = DateTimeUtil.tryParse(startStored);
                DateTimeUtil.ParsedDateTime pe = DateTimeUtil.tryParse(endStored);

                if (ps != null && pe != null) {
                    task = new Event(description, ps.dateTime, "1".equals(startFlag) || ps.hasTime,
                            pe.dateTime, "1".equals(endFlag) || pe.hasTime);
                } else {
                    task = new Event(description, parts[3], parts[4]);
                }
            } else {
                // Legacy format: E | status | desc | start | end
                task = new Event(description, parts[3], parts[4]);
            }
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
