package chitti.ui;

import java.io.IOException;

import chitti.Chitti;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Chitti using FXML.
 */
public class Main extends Application {

    private Chitti chitti = new Chitti("./data/chitti.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Chitti Chatbot");
            stage.setMinHeight(600.0);
            stage.setMinWidth(400.0);
            fxmlLoader.<MainWindow>getController().setChitti(chitti);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
