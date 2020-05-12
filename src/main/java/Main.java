import data.gameEngine.GameLogic;
import data.gui.UserInterface;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class.toString());

    public static void main(String[] args) {
        log.info("Starting game!");

        GameLogic game = new GameLogic();
        UserInterface ui = new UserInterface(game);

        ExecutorService es = Executors.newSingleThreadScheduledExecutor();
        es.execute(ui);

        try {
            es.shutdown();
            es.awaitTermination(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        game.setUpdatable(ui.getUpdatable());
    }
}
