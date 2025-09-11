package chitti.task;

/**
 * Base type for all tasks managed by the app.
 */
public class Task {
    private String description;
    private boolean isDone;

    /**
     * Constructs a Task with the given description.
     *
     * @param description the task description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns an "X" for done or a space for not done, for list rendering.
     */
    public String getStatusIcon() {
        return (this.isDone ? "X" : " ");
    }

    /** Marks this task as done. */
    public void markAsDone() {
        this.isDone = true;
    }

    /** Marks this task as not done. */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /** Returns true if this task is marked done. */
    public boolean isMarked() {
        return this.isDone;
    }

    /**
     * Returns the description of this task.
     *
     * @return the task description
     */
    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}
