import data.gameEngine.GameLogic;
import data.gui.UserInterface;
import data.movables.MovableFactory;
import locations.LocationsManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class.toString());

    public static void main(String[] args) throws InterruptedException {
        GameLogic game = startMenu();

        log.info("Starting game!");

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
            log.log(Level.WARNING,"Couldn't clean files before startup!", e);
            return;
        }
    }

    private static GameLogic startMenu() {
        String name;
        int profession;
        try (Scanner reader = new Scanner(System.in)) {
            System.out.println("Welcome to Gwiezdna Flota ASCII");
            while (true) {
                System.out.println("Please type name of your character (max 10 characters):");
                name = reader.nextLine();
                if (name.length() > 10) {
                    System.out.println("Too long name!");
                    continue;
                }
                break;
            }

            while (true) {
                System.out.println("Choose your profession ([1] Solider, [2] Recon):");
                if (!reader.hasNextInt()) {
                    System.out.println("Only numbers are allowed!");
                    reader.next();
                    continue;
                }
                profession = reader.nextInt();
                if (profession > 2 || profession < 1) {
                    System.out.println("Wrong profession number!");
                    continue;
                }
                break;

            }
        }
        return new GameLogic(new MovableFactory(), LocationsManager.getInstance(), name, profession);
    }

}
