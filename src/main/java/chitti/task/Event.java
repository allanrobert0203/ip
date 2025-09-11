package chitti.task;

import java.time.LocalDateTime;

import chitti.util.DateTimeUtil;

/**
 * Bounded task that spans a start and end date/time.
 */
public class Event extends Task {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private boolean startHasTime;
    private boolean endHasTime;

    /**
     * Constructs an Event with the given description and string representations of start and end times.
     * If parsing fails, defaults are used for the date/time values.
     *
     * @param description the event description
     * @param start the start date/time string to parse
     * @param end the end date/time string to parse
     */
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

    /**
     * Constructs an Event with the given description and explicit date/time values.
     *
     * @param description the event description
     * @param start the start date/time
     * @param startHasTime whether the start time includes time component
     * @param end the end date/time
     * @param endHasTime whether the end time includes time component
     */
    public Event(String description, LocalDateTime start, boolean startHasTime, LocalDateTime end, boolean endHasTime) {
        super(description);
        this.startDateTime = start;
        this.startHasTime = startHasTime;
        this.endDateTime = end;
        this.endHasTime = endHasTime;
    }

    /**
     * Returns the start date/time of the event.
     *
     * @return the start date/time
     */
    public LocalDateTime getStartDateTime() {
        return this.startDateTime;
    }

    /**
     * Returns the end date/time of the event.
     *
     * @return the end date/time
     */
    public LocalDateTime getEndDateTime() {
        return this.endDateTime;
    }

    /**
     * Returns whether the start date/time includes a time component.
     *
     * @return true if start has time component, false otherwise
     */
    public boolean ishasStartTime() {
        return this.startHasTime;
    }

    /**
     * Returns whether the end date/time includes a time component.
     *
     * @return true if end has time component, false otherwise
     */
    public boolean ishasEndTime() {
        return this.endHasTime;
    }

    /**
     * Returns a string representation of the event.
     *
     * @return string representation of the event
     */
    public String toString() {
        String startStr = DateTimeUtil.formatForDisplay(startDateTime, startHasTime);
        String endStr = DateTimeUtil.formatForDisplay(endDateTime, endHasTime);
        return "[E]" + super.toString() + " (from: " + startStr + " to: " + endStr + ")";
    }
}
