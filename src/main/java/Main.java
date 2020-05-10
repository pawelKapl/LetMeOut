import data.gameEngine.GameLogic;
import data.gui.UserInterface;

import javax.swing.SwingUtilities;
import java.util.logging.Logger;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class.toString());

    public static void main(String[] args) {
        log.info("Starting game!");

        GameLogic game = new GameLogic();

        UserInterface ui = new UserInterface(game);
        SwingUtilities.invokeLater(ui);

        while (ui.getUpdatable() == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                log.info("The drawing board hasn't been created yet. Waiting!");
            }
        }
        game.setUpdatable(ui.getUpdatable());
    }
}
