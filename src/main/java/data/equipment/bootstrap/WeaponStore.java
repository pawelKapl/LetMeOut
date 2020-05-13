package data.equipment.bootstrap;

import data.equipment.weapons.CasualWeapon;
import data.equipment.weapons.Weapon;

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

public class WeaponStore implements Map<Integer, Weapon> {

    private Map<Integer, Weapon> weaponStore = new HashMap<>();

    private final Logger log = Logger.getLogger(WeaponStore.class.toString());

    public WeaponStore() {
        log.info("Bootstrapping weapon store");
        bootstrap();
    }

    private void bootstrap() {

        Path path = Paths.get("src/resources/weapons.txt");
        List<String> weapons = new ArrayList<>();

        try {
            weapons = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
            log.warning("Troubles with reading file!" + path.getFileName());
        }

        int i = 0;
        for (String item : weapons) {
            String[] itemData = item.split(";");
            Weapon weapon = new CasualWeapon();
            weapon.setName(itemData[0]);
            weapon.setDescription(itemData[1]);
            weapon.setAttack(Integer.parseInt(itemData[2]));
            weaponStore.put(i++, weapon);
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
