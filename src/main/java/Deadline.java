public class Deadline extends Task {
    protected String description;
    protected Boolean isDone;
    protected String date;

    public Deadline(String description, String date) {
        super(description);
        this.isDone = false;
        this.date = date;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.date + ")";
    }
}
