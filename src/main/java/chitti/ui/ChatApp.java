package chitti.ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import chitti.command.Command;
import chitti.command.Parser;
import chitti.exception.ChittiException;
import chitti.storage.Storage;
import chitti.task.TaskList;
import javafx.application.Application;
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

        ScrollPane scrollPane = new ScrollPane(this.messages);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        this.inputField = new TextField();
        this.inputField.setPromptText("Enter command (e.g., todo read book)");

        Button sendButton = new Button("Send");
        sendButton.setDefaultButton(true);
        sendButton.setOnAction(e -> handleSend(stage));
        this.inputField.setOnAction(e -> handleSend(stage));

        HBox inputBar = new HBox(8);
        inputBar.setPadding(new Insets(8));
        this.userAvatar = loadAvatar("/images/DaUser.png", 24);
        this.botAvatar = loadAvatar("/images/DaChitti.png", 24);
        HBox.setHgrow(this.inputField, Priority.ALWAYS);
        inputBar.getChildren().addAll(this.userAvatar, this.inputField, sendButton);

        BorderPane root = new BorderPane();
        root.setCenter(scrollPane);
        root.setBottom(inputBar);
        BorderPane.setMargin(scrollPane, new Insets(8));

        Scene scene = new Scene(root, 640, 480);
        stage.setTitle("Chitti Chatbot");
        stage.setScene(scene);
        stage.show();

        appendBot("Hello! I'm Chitti the robot. Speed 1 terahertz, memory 1 zigabyte."
                + "Type commands like list, todo, deadline, event, on, delete, mark, unmark, bye.");
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

        if (exit) {
            stage.close();
        }
    }

    private void appendUser(String message) {
        HBox row = new HBox(8);
        row.setAlignment(Pos.TOP_RIGHT);
        Label text = new Label(message);
        text.setWrapText(true);
        text.maxWidthProperty().bind(this.messages.widthProperty().subtract(80));
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        row.getChildren().addAll(spacer, text, loadAvatar("/images/DaUser.png", 24));
        this.messages.getChildren().add(row);
    }

    private void appendBot(String message) {
        HBox row = new HBox(8);
        row.getChildren().add(loadAvatar("/images/DaChitti.png", 24));
        Label text = new Label(message);
        text.setWrapText(true);
        text.maxWidthProperty().bind(this.messages.widthProperty().subtract(80));
        row.getChildren().add(text);
        this.messages.getChildren().add(row);
    }

    private ImageView loadAvatar(String resourcePath, double size) {
        try {
            Image img = new Image(this.getClass().getResourceAsStream(resourcePath));
            ImageView view = new ImageView(img);
            view.setFitHeight(size);
            view.setFitWidth(size);
            return view;
        } catch (Exception e) {
            return new ImageView();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}


