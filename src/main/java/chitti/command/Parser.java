package chitti.command;

import chitti.exception.ChittiException;

/**
 * Parses raw user input into executable commands.
 */
public class Parser {

    /**
     * Parses an input line to a specific Command instance.
     * @param fullCommand raw user input
     * @return a concrete Command to execute
     * @throws ChittiException if the command is unknown or invalid
     */
    public static Command parse(String fullCommand) throws ChittiException {
        String input = fullCommand.trim();

        if (input.isEmpty()) {
            throw new ChittiException("Empty command.");
        }

        return parseNonEmptyInput(input);
    }

    /**
     * Parses non-empty input strings into specific commands.
     *
     * @param input the trimmed, non-empty user input
     * @return a concrete Command to execute
     * @throws ChittiException if the command is unknown or invalid
     */
    private static Command parseNonEmptyInput(String input) throws ChittiException {
        if (input.equals("bye")) {
            return new ExitCommand();
        }

        if (input.equals("list")) {
            return new ListCommand();
        }

        if (input.equals("findduplicates") || input.equals("checkduplicates")) {
            return new FindDuplicatesCommand();
        }

        if (input.startsWith("mark ")) {
            String argument = input.substring(5);
            return new MarkCommand(argument);
        }

        if (input.startsWith("unmark ")) {
            String argument = input.substring(7);
            return new UnmarkCommand(argument);
        }

        if (input.startsWith("todo ")) {
            String argument = input.substring(5);
            return new AddTodoCommand(argument);
        }

        if (input.equals("todo")) {
            throw new ChittiException("The description of a todo cannot be empty. "
                    + "Use the following format: todo <description>");
        }

        if (input.startsWith("deadline ")) {
            String argument = input.substring(9);
            return new AddDeadlineCommand(argument);
        }

        if (input.equals("deadline")) {
            throw new ChittiException("The description of a deadline cannot be empty. "
                    + "Use the following format: deadline <description> /by <duedate>");
        }

        if (input.startsWith("event ")) {
            String argument = input.substring(6);
            return new AddEventCommand(argument);
        }

        if (input.equals("event")) {
            throw new ChittiException("The description of an event cannot be empty. "
                    + "Use the following format: event <description> /from <time> /to <time>");
        }

        if (input.startsWith("delete ")) {
            String argument = input.substring(7);
            return new DeleteCommand(argument);
        }

        if (input.startsWith("on ")) {
            String argument = input.substring(3);
            return new OnDateCommand(argument);
        }

        if (input.startsWith("find ")) {
            String argument = input.substring(5);
            return new FindCommand(argument);
        }

        throw new ChittiException("I'm sorry, but I don't know what that means 😭");
    }
}
