import data.gameEngine.GameLogic;
import data.gui.UserInterface;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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


        Path path = Paths.get("src/resources/savedLocations/");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            cleanDirectory(path);
        }));
    }

    private static void cleanDirectory(Path path) {
        log.info("Cleaning works! Erasing temp files!");
        try {
            Files.walk(path)
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            e.printStackTrace();
            log.info("Couldn't clean files before startup!");
            return;
        }
    }
}
