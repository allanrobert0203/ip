public class Event extends Task{

    protected String description;
    protected Boolean isDone;
    protected String startDateTime;
    protected String endDateTime;

    public Event(String description, String startDateTime, String endDateTimee) {
        super(description);
        this.isDone = false;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTimee;
    }

    public String toString() {
        return "[E]" + super.toString() + " (from: " + startDateTime + " to: " + endDateTime + ")";
    }
}
