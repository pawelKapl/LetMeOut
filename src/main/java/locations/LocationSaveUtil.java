package locations;

import static java.lang.String.format;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;

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

  private static final String SAVED_LOCATIONS_PATH = "./src/resources/savedLocations/%s.dat";
  private static final Logger LOG = Logger.getLogger(LocationSaveUtil.class.toString());

  private LocationSaveUtil() {}

  public static void saveLocation(Terrain terrain, List<Enemy> enemies, FogOfWar fog,
      Coords player) {
    LOG.log(INFO,"Saving Location {0} to file...", terrain.getName());

    String filename = prepareFileName(terrain.getName());
    Path path = Paths.get(
        format(SAVED_LOCATIONS_PATH, prepareFileName(filename)));

    try (ObjectOutputStream file = new ObjectOutputStream(
        new BufferedOutputStream(Files.newOutputStream(path)))) {
      file.writeObject(terrain);
      file.writeObject(enemies);
      file.writeObject(fog);
      file.writeObject(player);
    } catch (IOException e) {
      LOG.log(WARNING, "Error during location saving process", e);
    }
  }

  public static boolean loadLocation(GameLogic gameLogic, String location) {
    if (gameLogic.getTerrain() == null) {
      return false;
    }

    Path path = Paths.get(
        format(SAVED_LOCATIONS_PATH, prepareFileName(location)));

    if (!Files.exists(path)) {
      return false;
    }

    LOG.log(INFO, "Loading Location {0} from file...", location);

    try (ObjectInputStream file = new ObjectInputStream(
        new BufferedInputStream(Files.newInputStream(path)))) {
      gameLogic.setTerrain((Terrain) file.readObject());
      gameLogic.setEnemies((List<Enemy>) file.readObject());
      gameLogic.setFogOfWar((FogOfWar) file.readObject());
      gameLogic.getPlayer().setCoords((Coords) file.readObject());
    } catch (IOException | ClassNotFoundException e) {
      LOG.log(WARNING, "Error during reading location file {0}, error: {1}",
          new Object[]{path.getFileName(), e});
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
