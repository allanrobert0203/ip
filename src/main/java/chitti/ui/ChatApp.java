package chitti.ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import chitti.command.Command;
import chitti.command.Parser;
import chitti.exception.ChittiException;
import chitti.storage.Storage;
import chitti.task.TaskList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Simple JavaFX GUI for interacting with Chitti using the existing command system.
 */
public class ChatApp extends Application {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    private VBox messages;
    private TextField inputField;
    private ImageView botAvatar;
    private ImageView userAvatar;
    private ScrollPane scrollPane;

    @Override
    public void start(Stage stage) throws Exception {
        this.ui = new Ui();
        this.storage = new Storage("./data/chitti.txt");
        try {
            this.tasks = new TaskList(this.storage.load());
        } catch (Exception e) {
            this.tasks = new TaskList();
        }

        this.messages = new VBox(8);
        this.messages.setPadding(new Insets(8));
        this.messages.setFillWidth(true);

        this.scrollPane = new ScrollPane(this.messages);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Show vertical scrollbar when needed
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Never show horizontal scrollbar
        scrollPane.setPadding(new Insets(0));

        // CSS styling for better scrollbar visibility
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        this.inputField = new TextField();
        this.inputField.setPromptText("Enter command (e.g., todo read book)");

        Button sendButton = new Button("Send");
        sendButton.setDefaultButton(true);
        sendButton.setOnAction(e -> handleSend(stage));
        this.inputField.setOnAction(e -> handleSend(stage));

        HBox inputBar = new HBox(8);
        inputBar.setPadding(new Insets(8));
        inputBar.setAlignment(Pos.CENTER_LEFT);
        this.userAvatar = loadAvatar("/images/DaUser.png", 24);
        this.botAvatar = loadAvatar("/images/DaChitti.png", 24);
        HBox.setHgrow(this.inputField, Priority.ALWAYS);
        inputBar.getChildren().addAll(this.userAvatar, this.inputField, sendButton);

        BorderPane root = new BorderPane();
        root.setCenter(scrollPane);
        root.setBottom(inputBar);
        BorderPane.setMargin(scrollPane, new Insets(0));

        Scene scene = new Scene(root, 640, 480);
        stage.setTitle("Chitti Chatbot");
        stage.setScene(scene);
        stage.show();

        appendBot("Hello! I'm Chitti the robot. Speed 1 terahertz, memory 1 zigabyte. " +
                "Type commands like list, todo, deadline, event, on, delete, mark, unmark, bye.");

        // Initial scroll to bottom
        scrollToBottom();
    }

    private void handleSend(Stage stage) {
        String text = this.inputField.getText();
        if (text == null || text.trim().isEmpty()) {
            return;
        }
        appendUser(text);
        this.inputField.clear();

        // Capture System.out during command execution so we can render output in the GUI
        PrintStream originalOut = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream capture = new PrintStream(buffer);
        System.setOut(capture);

        boolean exit = false;
        try {
            Command c = Parser.parse(text);
            c.execute(this.tasks, this.ui, this.storage);
            exit = c.isExit();
        } catch (ChittiException ce) {
            ui.showError(ce.getMessage());
        } catch (Exception ex) {
            ui.showError("Oops! Something unexpected went wrong. Please try again.");
        } finally {
            System.out.flush();
            System.setOut(originalOut);
        }

        String botOut = buffer.toString().trim();
        if (!botOut.isEmpty()) {
            appendBot(botOut);
        }

        // Scroll to the bottom after adding new messages
        scrollToBottom();

        if (exit) {
            stage.close();
        }
    }

    private void appendUser(String message) {
        HBox row = new HBox(8);
        row.setAlignment(Pos.TOP_RIGHT);
        row.setPadding(new Insets(4, 8, 4, 8));

        Label text = new Label(message);
        text.setWrapText(true);
        text.setMaxWidth(this.messages.getWidth() - 100); // Better width calculation
        text.setStyle("-fx-background-color: #000000; -fx-padding: 8px; -fx-background-radius: 12px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        ImageView avatar = loadAvatar("/images/DaUser.png", 32);
        row.getChildren().addAll(spacer, text, avatar);
        this.messages.getChildren().add(row);
    }

    private void appendBot(String message) {
        HBox row = new HBox(8);
        row.setAlignment(Pos.TOP_LEFT);
        row.setPadding(new Insets(4, 8, 4, 8));

        ImageView avatar = loadAvatar("/images/DaChitti.png", 32);
        Label text = new Label(message);
        text.setWrapText(true);
        text.setMaxWidth(this.messages.getWidth() - 100); // Better width calculation
        text.setStyle("-fx-background-color: black; -fx-padding: 8px; -fx-background-radius: 12px;");

        row.getChildren().addAll(avatar, text);
        this.messages.getChildren().add(row);
    }

    private ImageView loadAvatar(String resourcePath, double size) {
        try {
            Image img = new Image(this.getClass().getResourceAsStream(resourcePath));
            ImageView view = new ImageView(img);
            view.setFitHeight(size);
            view.setFitWidth(size);
            view.setPreserveRatio(true);
            return view;
        } catch (Exception e) {
            // Return a placeholder if image fails to load
            ImageView placeholder = new ImageView();
            placeholder.setFitHeight(size);
            placeholder.setFitWidth(size);
            placeholder.setStyle("-fx-background-color: lightgray; -fx-border-radius: 50%;");
            return placeholder;
        }
    }

    /**
     * Scrolls the scroll pane to the bottom automatically.
     * This ensures the latest messages are always visible.
     */
    private void scrollToBottom() {
        Platform.runLater(() -> {
            scrollPane.setVvalue(1.0); // Scroll to bottom
            scrollPane.applyCss();
            scrollPane.layout();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
