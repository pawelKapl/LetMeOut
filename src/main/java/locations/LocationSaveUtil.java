package locations;

import data.gameEngine.FogOfWar;
import data.gameEngine.GameLogic;
import data.movables.Coords;
import data.movables.enemies.Enemy;
import data.terrains.Terrain;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class LocationSaveUtil {

    private static final Logger log = Logger.getLogger(LocationSaveUtil.class.toString());

    public static void saveLocation(Terrain terrain, List<Enemy> enemies, FogOfWar fog , Coords player) {
        log.info("Saving Location " + terrain.getName() + " to file...");

        String filename = prepareFileName(terrain.getName());

        Path path = Paths.get(String.format("src/resources/savedLocations/%s.dat", prepareFileName(filename)));

        try (ObjectOutputStream file = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(path)))) {
            file.writeObject(terrain);
            file.writeObject(enemies);
            file.writeObject(fog);
            file.writeObject(player);
        } catch (IOException e) {
            e.printStackTrace();
            log.warning("Error during location saving process");
            return;
        }
    }

    public static boolean loadLocation(GameLogic gameLogic, String location) {
        //triggers only on first run
        if (gameLogic.getTerrain() == null) {
            return false;
        }

        Path path = Paths.get(String.format("src/resources/savedLocations/%s.dat", prepareFileName(location)));

        if (!Files.exists(path)) {
            return false;
        }

        log.info("Loading Location " + location + " from file...");

        try (ObjectInputStream file = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(path)))){
            gameLogic.setTerrain((Terrain) file.readObject());
            gameLogic.setEnemies((List<Enemy>) file.readObject());
            gameLogic.setFogOfWar((FogOfWar) file.readObject());
            gameLogic.getPlayer().setCoords((Coords) file.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            log.warning("Error during reading location file " + path.getFileName());
            return false;
        }
        return true;
    }

    private static String prepareFileName(String location) {
        return Arrays.stream(location.split(" "))
                .map(String::toLowerCase)
                .collect(Collectors.joining());
    }
}
