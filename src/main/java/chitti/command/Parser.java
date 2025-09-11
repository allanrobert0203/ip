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

        if (input.equals("bye")) {
            return new ExitCommand();
        } else if (input.equals("list")) {
            return new ListCommand();
        } else if (input.startsWith("mark ")) {
            return new MarkCommand(input.substring(5));
        } else if (input.startsWith("unmark ")) {
            return new UnmarkCommand(input.substring(7));
        } else if (input.equals("todo")) {
            throw new ChittiException("The description of a todo cannot be empty. "
                    + "Use the following format: todo <description>");
        } else if (input.startsWith("todo ")) {
            return new AddTodoCommand(input.substring(5));
        } else if (input.equals("deadline")) {
            throw new ChittiException("The description of a deadline cannot be empty. "
                    + "Use the following format: deadline <description> /by <duedate>");
        } else if (input.startsWith("deadline ")) {
            return new AddDeadlineCommand(input.substring(9));
        } else if (input.equals("event")) {
            throw new ChittiException("The description of an event cannot be empty. "
                    + "Use the following format: event <description> /from <time> /to <time>");
        } else if (input.startsWith("event ")) {
            return new AddEventCommand(input.substring(6));
        } else if (input.startsWith("delete ")) {
            return new DeleteCommand(input.substring(7));
        } else if (input.startsWith("on ")) {
            return new OnDateCommand(input.substring(3));
        } else if (input.startsWith("find ")) {
            return new FindCommand(input.substring(5));
        } else {
            throw new ChittiException("I'm sorry, but I don't know what that means 😭");
        }
    }
}


