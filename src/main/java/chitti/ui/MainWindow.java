package chitti.ui;

import chitti.Chitti;
import chitti.command.Command;
import chitti.command.Parser;
import chitti.exception.ChittiException;
import chitti.storage.Storage;
import chitti.task.TaskList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Chitti chitti;
    private Storage storage;
    private TaskList tasks;
    private GuiUi guiUi = new GuiUi();

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image chittiImage = new Image(this.getClass().getResourceAsStream("/images/DaChitti.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Chitti instance */
    public void setChitti(Chitti c) {
        chitti = c;
        try {
            this.storage = new Storage("./data/chitti.txt");
            this.tasks = new TaskList(this.storage.load());
        } catch (Exception e) {
            this.storage = new Storage("./data/chitti.txt");
            this.tasks = new TaskList();
        }
        // Show welcome message using GUI-specific UI
        appendChittiDialog(guiUi.welcome());
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Chitti's reply
     * and then appends them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input == null || input.trim().isEmpty()) {
            return;
        }

        appendUserDialog(input);
        userInput.clear();

        try {
            Command c = Parser.parse(input);

            // Capture System.out during command execution
            PrintStream originalOut = System.out;
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            PrintStream capture = new PrintStream(buffer);
            System.setOut(capture);
            try {
                c.execute(this.tasks, new Ui(), this.storage);
            } finally {
                System.out.flush();
                System.setOut(originalOut);
            }

            String response = buffer.toString().trim();
            if (!response.isEmpty()) {
                appendChittiDialog(response);
            }

            if (c.isExit()) {
                javafx.application.Platform.exit();
            }
        } catch (ChittiException e) {
            appendChittiDialog(guiUi.showError(e.getMessage()));
        } catch (Exception e) {
            appendChittiDialog(guiUi.showError("Something unexpected went wrong. Please try again."));
        }
    }

    private void appendUserDialog(String text) {
        dialogContainer.getChildren().add(DialogBox.getUserDialog(text, userImage));
    }

    private void appendChittiDialog(String text) {
        dialogContainer.getChildren().add(DialogBox.getChittiDialog(text, chittiImage));
    }
}
