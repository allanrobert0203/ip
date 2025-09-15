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
        outputBuffer.append(message).append("\n");
        return message;
    }

    /**
     * Shows an error message and captures it for GUI display
     */
    public String showError(String error) {
        String errorMessage = "Error: " + error;
        outputBuffer.append(errorMessage).append("\n");
        return errorMessage;
    }

    /**
     * Welcome message for GUI
     */
    public String welcome() {
        return "Hello! I'm Chitti the robot. Speed 1 terahertz, memory 1 zigabyte.\n"
                + "Type commands like list, todo, deadline, event, on, delete, mark, unmark, bye.";
    }

    /**
     * Captures output from CLI-style Ui methods (mimicking the original Ui interface)
     * These methods are used by Command.execute() calls
     */
    public void print(String message) {
        outputBuffer.append(message);
    }

    public void println(String message) {
        outputBuffer.append(message).append("\n");
    }

    public void printf(String format, Object... args) {
        outputBuffer.append(String.format(format, args));
    }

    /**
     * Gets the captured output and clears the buffer
     */
    public String getCapturedOutput() {
        String output = outputBuffer.toString().trim();
        clearOutput();
        return output;
    }

    /**
     * Clears the output buffer
     */
    public void clearOutput() {
        outputBuffer = new StringBuilder();
    }

    /**
     * Additional methods to mimic the original Ui interface if needed
     */
    public void showLine() {
        outputBuffer.append("____________________________________________________________\n");
    }

    public void showWelcome() {
        outputBuffer.append(welcome()).append("\n");
    }
}
