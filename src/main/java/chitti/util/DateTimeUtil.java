package chitti.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utilities for parsing and formatting dates/times in user-facing and storage formats.
 */
public class DateTimeUtil {

    /**
     * Result of parsing a date/time string.
     */
    public static class ParsedDateTime {
        public final LocalDateTime dateTime;
        public final boolean hasTime;

        /**
         * Constructs a ParsedDateTime object with the given date/time and time presence flag.
         *
         * @param dateTime the parsed date and time
         * @param hasTime whether the parsed string included time information
         */
        public ParsedDateTime(LocalDateTime dateTime, boolean hasTime) {
            this.dateTime = dateTime;
            this.hasTime = hasTime;
        }
    }

    private static final DateTimeFormatter DATE_ONLY_ISO = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_ONLY_SLASH = DateTimeFormatter.ofPattern("d/M/yyyy");
    private static final DateTimeFormatter DATE_TIME_ISO_COMPACT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter DATE_TIME_SLASH_COMPACT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    /**
     * Attempts to parse an input as either a date or a date-time.
     * Supports yyyy-MM-dd, yyyy-MM-dd HHmm, d/M/yyyy, d/M/yyyy HHmm.
     * @param input raw input string
     * @return ParsedDateTime if successful; null otherwise
     */
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
        } catch (DateTimeParseException e) {
            // Continue to next parsing format
        }
        // d/M/yyyy HHmm
        try {
            LocalDateTime dt = LocalDateTime.parse(trimmed, DATE_TIME_SLASH_COMPACT);
            return new ParsedDateTime(dt, true);
        } catch (DateTimeParseException e) {
            // Continue to next parsing format
        }
        // Try date-only patterns -> no time component
        try {
            LocalDate d = LocalDate.parse(trimmed, DATE_ONLY_ISO);
            return new ParsedDateTime(d.atStartOfDay(), false);
        } catch (DateTimeParseException e) {
            // Continue to next parsing format
        }
        try {
            LocalDate d = LocalDate.parse(trimmed, DATE_ONLY_SLASH);
            return new ParsedDateTime(d.atStartOfDay(), false);
        } catch (DateTimeParseException e) {
            // All parsing attempts failed
        }
        return null;
    }

    /**
     * Formats a date/time for display, e.g., "Oct 15 2019, 6pm" or "Oct 15 2019".
     * @param dateTime the date and time to format
     * @param hasTime whether to include time in the formatted string
     * @return formatted date/time string
     */
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

    /**
     * Formats a date/time for storage: yyyy-MM-dd or yyyy-MM-dd HHmm when time is present.
     * @param dateTime the date and time to format
     * @param hasTime whether to include time in the formatted string
     * @return formatted date/time string for storage
     */
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
