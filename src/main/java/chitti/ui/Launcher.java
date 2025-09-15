package chitti.ui;

import chitti.Chitti;
import javafx.application.Application;

/**
 * Launcher that supports both GUI and CLI modes.
 * Use: `./gradlew run --args="cli"` for CLI; otherwise starts the GUI.
 */
public class Launcher {
    public static void main(String[] args) {
        if (args != null && args.length > 0 && "cli".equalsIgnoreCase(args[0])) {
            new Chitti("./data/chitti.txt").run();
        } else {
            Application.launch(Main.class, args);
        }
    }
}
