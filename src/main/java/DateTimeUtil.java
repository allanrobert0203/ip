import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {

    public static class ParsedDateTime {
        public final LocalDateTime dateTime;
        public final boolean hasTime;

        public ParsedDateTime(LocalDateTime dateTime, boolean hasTime) {
            this.dateTime = dateTime;
            this.hasTime = hasTime;
        }
    }

    private static final DateTimeFormatter DATE_ONLY_ISO = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_ONLY_SLASH = DateTimeFormatter.ofPattern("d/M/yyyy");
    private static final DateTimeFormatter DATE_TIME_ISO_COMPACT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter DATE_TIME_SLASH_COMPACT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    public static ParsedDateTime tryParse(String input) {
        if (input == null) {
            return null;
        }
        String trimmed = input.trim();
        // Try date-time first
        // yyyy-MM-dd HHmm
        try {
            LocalDateTime dt = LocalDateTime.parse(trimmed, DATE_TIME_ISO_COMPACT);
            return new ParsedDateTime(dt, true);
        } catch (DateTimeParseException ignored) {
        }
        // d/M/yyyy HHmm
        try {
            LocalDateTime dt = LocalDateTime.parse(trimmed, DATE_TIME_SLASH_COMPACT);
            return new ParsedDateTime(dt, true);
        } catch (DateTimeParseException ignored) {
        }
        // Try date-only patterns -> no time component
        try {
            LocalDate d = LocalDate.parse(trimmed, DATE_ONLY_ISO);
            return new ParsedDateTime(d.atStartOfDay(), false);
        } catch (DateTimeParseException ignored) {
        }
        try {
            LocalDate d = LocalDate.parse(trimmed, DATE_ONLY_SLASH);
            return new ParsedDateTime(d.atStartOfDay(), false);
        } catch (DateTimeParseException ignored) {
        }
        return null;
    }

    public static String formatForDisplay(LocalDateTime dateTime, boolean hasTime) {
        if (dateTime == null) {
            return "";
        }
        String datePart = dateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        if (!hasTime) {
            return datePart;
        }
        String timePart = dateTime.format(DateTimeFormatter.ofPattern("h:mm a")).toLowerCase();
        if (timePart.contains(":00")) {
            timePart = timePart.replace(":00", "");
        }
        timePart = timePart.replace(" ", ""); // "6 pm" -> "6pm"
        return datePart + ", " + timePart;
    }

    public static String formatForStorage(LocalDateTime dateTime, boolean hasTime) {
        if (dateTime == null) {
            return "";
        }
        if (hasTime) {
            return dateTime.format(DATE_TIME_ISO_COMPACT);
        }
        return dateTime.format(DATE_ONLY_ISO);
    }
}


