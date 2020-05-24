import data.gameEngine.GameLogic;
import data.gui.UserInterface;
import data.movables.MovableFactory;
import locations.LocationsManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class.toString());

    public static void main(String[] args) throws InterruptedException {
        log.info("Starting game!");

        GameLogic game = new GameLogic(new MovableFactory(), new LocationsManager());
        UserInterface ui = new UserInterface(game);

        ExecutorService es = Executors.newSingleThreadScheduledExecutor();
        es.execute(ui);

        es.shutdown();
        es.awaitTermination(1000, TimeUnit.MILLISECONDS);

        game.setUpdatable(ui.getUpdatable());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            cleanDirectory();
        }));
    }

    private static void cleanDirectory() {
        log.info("Cleaning works! Erasing temp files!");
        Path path = Paths.get("src/resources/savedLocations/");

        try {
            Files.walk(path)
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            log.info("Couldn't clean files before startup!");
            log.log(Level.WARNING,"Couldn't clean files before startup!", e);
            return;
        }
    }
}
