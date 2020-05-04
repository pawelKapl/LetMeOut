import data.gameEngine.Game;
import data.gui.GameBoard;
import data.gui.UserInterface;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.util.logging.Logger;

public class Main {

    private static JFrame window;
    private static GameBoard gameBoard;

    private static final Logger log = Logger.getLogger(Main.class.toString());

    public static void main(String[] args) {
        log.info("Starting game");

        UserInterface ui = new UserInterface(new Game());
        SwingUtilities.invokeLater(ui);

        while (ui.getUpdatable() == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                log.info("The drawing board hasn't been created yet.");
            }
        }
    }
}
