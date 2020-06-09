package data.equipment.bootstrap;

import data.equipment.weapons.CasualWeapon;
import data.equipment.weapons.Weapon;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class WeaponStore implements Map<Integer, Weapon> {

    private final Map<Integer, Weapon> weaponStore = new HashMap<>();

    private static final Logger log = Logger.getLogger(WeaponStore.class.toString());

    private WeaponStore() {
        log.info("Bootstrapping weapon store");
        bootstrap();
    }

    public static WeaponStore getInstance() {
        return new WeaponStore();
    }

    private void bootstrap() {
        Path path = Paths.get("src/resources/bootstrapFiles/weapons.txt");
        List<String> weapons = null;

        try {
            weapons = Files.readAllLines(path);
        } catch (IOException e) {
            log.log(Level.WARNING,"Troubles with reading file!" + path.getFileName(), e);
        }

        int i = 0;
        for (String item : weapons) {
            String[] itemData = item.split(";");
            weaponStore.put(i++, CasualWeapon.getInstance(itemData[0], itemData[1], Integer.parseInt(itemData[2])));
        }
    }


    @Override
    public int size() {
        return weaponStore.size();
    }

    @Override
    public boolean isEmpty() {
        return weaponStore.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return weaponStore.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return weaponStore.containsValue(value);
    }

    @Override
    public Weapon get(Object key) {
        return weaponStore.get(key);
    }

    @Override
    public Weapon put(Integer key, Weapon value) {
        return weaponStore.put(key, value);
    }

    @Override
    public Weapon remove(Object key) {
        return weaponStore.remove(key);
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Weapon> m) {
        weaponStore.putAll(m);
    }

    @Override
    public void clear() {
        weaponStore.clear();
    }

    @Override
    public Set<Integer> keySet() {
        return weaponStore.keySet();
    }

    @Override
    public Collection<Weapon> values() {
        return weaponStore.values();
    }

    @Override
    public Set<Entry<Integer, Weapon>> entrySet() {
        return weaponStore.entrySet();
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
