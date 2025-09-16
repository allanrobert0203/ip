package chitti.ui;

/**
 * UI class specifically for GUI interactions.
 * This avoids modifying the existing CLI Ui class.
 */
public class GuiUi {
    private StringBuilder outputBuffer = new StringBuilder();

    /**
     * Shows a message and captures it for GUI display
     */
    public String showMessage(String message) {
        assert message != null : "Message must not be null";
        outputBuffer.append(message).append("\n");
        return message;
    }

    /**
     * Shows an error message and captures it for GUI display
     */
    public String showError(String error) {
        assert error != null : "Error message must not be null";
        String errorMessage = "Error: " + error;
        outputBuffer.append(errorMessage).append("\n");
        return errorMessage;
    }

    /**
     * Welcome message for GUI
     */
    public String welcome() {
        String welcomeMsg = "Hello! I'm Chitti the robot. Speed 1 terahertz, memory 1 zigabyte.\n"
                + "Type commands like list, todo, deadline, event, on, delete, mark, unmark, bye.";
        assert welcomeMsg != null && !welcomeMsg.isEmpty() : "Welcome message must be properly defined";
        return welcomeMsg;
    }

    /**
     * Captures output from CLI-style Ui methods (mimicking the original Ui interface)
     * These methods are used by Command.execute() calls
     */
    public void print(String message) {
        assert message != null : "Print message must not be null";
        outputBuffer.append(message);
    }

    /**
     * Appends a message to the output buffer followed by a newline.
     *
     * @param message the message to be appended to the output buffer
     * @throws AssertionError if the message parameter is null
     */
    public void println(String message) {
        assert message != null : "Println message must not be null";
        outputBuffer.append(message).append("\n");
    }

    /**
     * Appends a formatted message to the output buffer using the specified format string and arguments.
     * The formatted string is created using {@link String#format(String, Object...)}.
     *
     * @param format the format string as defined in {@link String#format(String, Object...)}
     * @param args the arguments referenced by the format specifiers in the format string
     * @throws AssertionError if the format parameter or args array is null
     */
    public void printf(String format, Object... args) {
        assert format != null : "Printf format must not be null";
        assert args != null : "Printf args must not be null";
        outputBuffer.append(String.format(format, args));
    }

    /**
     * Gets the captured output and clears the buffer
     */
    public String getCapturedOutput() {
        assert outputBuffer != null : "Output buffer must be properly initialized";
        String output = outputBuffer.toString().trim();
        clearOutput();
        assert output != null : "Captured output should not be null";
        return output;
    }

    /**
     * Clears the output buffer
     */
    public void clearOutput() {
        assert outputBuffer != null : "Output buffer must be properly initialized before clearing";
        outputBuffer = new StringBuilder();
        assert outputBuffer != null : "New output buffer must be properly initialized";
    }

    /**
     * Additional methods to mimic the original Ui interface if needed
     */
    public void showLine() {
        outputBuffer.append("____________________________________________________________\n");
    }

    /**
     * Displays a welcome message
     */
    public void showWelcome() {
        String welcomeMsg = welcome();
        assert welcomeMsg != null : "Welcome message must not be null";
        outputBuffer.append(welcomeMsg).append("\n");
    }
}
