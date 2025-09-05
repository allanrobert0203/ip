package chitti.task;

public class Task {
    public String description;
    boolean isDone;

    public Task (String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (this.isDone ? "X" : " ");
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public boolean isMarked() {
        return this.isDone;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}