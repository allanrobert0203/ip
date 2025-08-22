public class ToDo extends Task {

    protected String description;
    protected Boolean isDone;

    public ToDo (String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
