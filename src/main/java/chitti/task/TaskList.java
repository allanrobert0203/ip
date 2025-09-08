package chitti.task;

import java.util.ArrayList;
import java.util.List;

/**
 * Mutable container for tasks with basic list operations.
 */
public class TaskList {

    private final List<Task> tasks;

    /** Creates an empty task list. */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list from an existing list.
     * @param existing tasks to seed into this list
     */
    public TaskList(List<Task> existing) {
        this.tasks = new ArrayList<>(existing);
    }

    /** Returns the number of tasks. */
    public int size() {
        return this.tasks.size();
    }

    /** Returns true if the list has no tasks. */
    public boolean isEmpty() {
        return this.tasks.isEmpty();
    }

    /** Returns the task at the given index. */
    public Task get(int index) {
        return this.tasks.get(index);
    }

    /** Adds a task to the end of the list. */
    public void add(Task task) {
        this.tasks.add(task);
    }

    /** Removes and returns the task at the given index. */
    public Task remove(int index) {
        return this.tasks.remove(index);
    }

    /** Returns the backing list for persistence. */
    public List<Task> getTasks() {
        return this.tasks;
    }
}


