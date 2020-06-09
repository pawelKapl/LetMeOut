package locations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class LocationsManager implements Map<String, Location> {

    private final Map<String, Location> locations = new HashMap<>();
    private static final LocationsManager instance = new LocationsManager();

    private static Logger log;


    private LocationsManager() {
        log = Logger.getLogger(LocationsManager.class.toString());
        bootstrap();
    }

    public static LocationsManager getInstance() {
        return instance;
    }

    private void bootstrap() {

        Path path = Paths.get("src/resources/bootstrapFiles/locations-gameplay.txt");

        List<String> strings = new ArrayList<>();

        try {
            strings = Files.readAllLines(path);
        } catch (IOException e) {
            log.log(Level.WARNING,"Troubles with reading file!" + path.getFileName(), e);
        }

        for (String location : strings) {
            String[] details = location.split(";");


            Map<Integer, String> exits = new HashMap<>();
            for (int j = 7; j < details.length; j++) {
                exits.put(j - 7, details[j]);
            }

            locations.put(details[0], Location.getInstance(details[0], details[1],
                    details[2], details[3], details[4], details[5], details[6], exits));
        }
    }

    @Override
    public int size() {
        return locations.size();
    }

    @Override
    public boolean isEmpty() {
        return locations.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return locations.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return locations.containsValue(value);
    }

    @Override
    public Location get(Object key) {
        return locations.get(key);
    }

    @Override
    public Location put(String key, Location value) {
        return locations.put(key, value);
    }

    @Override
    public Location remove(Object key) {
        return locations.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends Location> m) {
        locations.putAll(m);
    }

    @Override
    public void clear() {
        locations.clear();
    }

    @Override
    public Set<String> keySet() {
        return locations.keySet();
    }

    @Override
    public Collection<Location> values() {
        return locations.values();
    }

    @Override
    public Set<Entry<String, Location>> entrySet() {
        return locations.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
