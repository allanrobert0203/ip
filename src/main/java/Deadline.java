import java.time.LocalDateTime;

public class Deadline extends Task {
    protected LocalDateTime dateTime;
    protected boolean hasTime;

    public Deadline(String description, String date) {
        super(description);
        DateTimeUtil.ParsedDateTime parsed = DateTimeUtil.tryParse(date);
        if (parsed == null) {
            // Fallback: keep as now at start of day but indicate no time
            this.dateTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
            this.hasTime = false;
        } else {
            this.dateTime = parsed.dateTime;
            this.hasTime = parsed.hasTime;
        }
    }

    public Deadline(String description, LocalDateTime dateTime, boolean hasTime) {
        super(description);
        this.dateTime = dateTime;
        this.hasTime = hasTime;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public boolean hasTimeComponent() {
        return this.hasTime;
    }

    @Override
    public String toString() {
        String formatted = DateTimeUtil.formatForDisplay(this.dateTime, this.hasTime);
        return "[D]" + super.toString() + " (by: " + formatted + ")";
    }
}
