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
import java.util.logging.Logger;

public class LocationsManager implements Map<String, Location> {

    private Map<String, Location> locations = new HashMap<>();

    private final Logger log = Logger.getLogger(this.getClass().toString());


    public LocationsManager() {
        log.info("Bootstrapping locations manager");
        bootstrap();
    }

    private void bootstrap() {

        Path path = Paths.get("src/resources/locations.txt");

        List<String> strings = new ArrayList<>();

        try {
            strings = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
            log.warning("Troubles with reading file!" + path.getFileName());
        }

        for (String location : strings) {
            String[] details = location.split(";");


            Map<Integer, String> exits = new HashMap<>();
            for (int j = 5; j < details.length; j++) {
                exits.put(j - 5, details[j]);
            }

            Location newLocation = new Location(details[0], details[1],
                    details[2], details[3], LocationType.valueOf(details[4]), exits);

            locations.put(newLocation.getName(), newLocation);
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
