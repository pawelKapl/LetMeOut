package data.equipment.bootstrap;

import data.equipment.armors.Armor;
import data.equipment.armors.CasualArmor;

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

public class ArmorStore implements Map<Integer, Armor> {

    private static Map<Integer, Armor> armorStore = new HashMap<>();

    private static final Logger log = Logger.getLogger(ArmorStore.class.toString());

    static {
        log.info("Bootstrapping armor store");
        Path path = Paths.get("src/resources/armors.txt");
        List<String> items = new ArrayList<>();
        try {
            items = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int i = 0;
        for (String item : items) {
            String[] itemData = item.split(";");
            Armor armor = new CasualArmor();
            armor.setName(itemData[0]);
            armor.setDescription(itemData[1]);
            armor.setDefence(Integer.parseInt(itemData[2]));
            armorStore.put(i++, armor);
        }
    }


    @Override
    public int size() {
        return armorStore.size();
    }

    @Override
    public boolean isEmpty() {
        return armorStore.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return armorStore.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return armorStore.containsValue(value);
    }

    @Override
    public Armor get(Object key) {
        return armorStore.get(key);
    }

    @Override
    public Armor put(Integer key, Armor value) {
        return armorStore.put(key, value);
    }

    @Override
    public Armor remove(Object key) {
        return armorStore.remove(key);
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Armor> m) {
        armorStore.putAll(m);
    }

    @Override
    public void clear() {
        armorStore.clear();
    }

    @Override
    public Set<Integer> keySet() {
        return armorStore.keySet();
    }

    @Override
    public Collection<Armor> values() {
        return armorStore.values();
    }

    @Override
    public Set<Entry<Integer, Armor>> entrySet() {
        return armorStore.entrySet();
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
