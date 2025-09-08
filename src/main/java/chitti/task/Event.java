package chitti.task;

import java.time.LocalDateTime;
import chitti.util.DateTimeUtil;

/**
 * Bounded task that spans a start and end date/time.
 */
public class Event extends Task {

    public LocalDateTime startDateTime;
    public LocalDateTime endDateTime;
    public boolean startHasTime;
    public boolean endHasTime;

    public Event(String description, String start, String end) {
        super(description);
        DateTimeUtil.ParsedDateTime pStart = DateTimeUtil.tryParse(start);
        DateTimeUtil.ParsedDateTime pEnd = DateTimeUtil.tryParse(end);
        if (pStart == null) {
            this.startDateTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
            this.startHasTime = false;
        } else {
            this.startDateTime = pStart.dateTime;
            this.startHasTime = pStart.hasTime;
        }
        if (pEnd == null) {
            this.endDateTime = this.startDateTime;
            this.endHasTime = this.startHasTime;
        } else {
            this.endDateTime = pEnd.dateTime;
            this.endHasTime = pEnd.hasTime;
        }
    }

    public Event(String description, LocalDateTime start, boolean startHasTime, LocalDateTime end, boolean endHasTime) {
        super(description);
        this.startDateTime = start;
        this.startHasTime = startHasTime;
        this.endDateTime = end;
        this.endHasTime = endHasTime;
    }

    public String toString() {
        String startStr = DateTimeUtil.formatForDisplay(startDateTime, startHasTime);
        String endStr = DateTimeUtil.formatForDisplay(endDateTime, endHasTime);
        return "[E]" + super.toString() + " (from: " + startStr + " to: " + endStr + ")";
    }
}
