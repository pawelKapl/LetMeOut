package data.equipment;

import data.equipment.armors.Armor;
import data.equipment.bootstrap.ArmorStore;
import data.equipment.bootstrap.WeaponStore;
import data.equipment.weapons.Weapon;
import data.movables.player.Player;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import static java.lang.StrictMath.random;

public class Equipment {

    private Player player;
    private int smallPotions = 2;
    private int largePotions = 1;

    private Map<Integer, Item> items = new TreeMap<>();
    private WeaponStore weaponStore = new WeaponStore();
    private ArmorStore armorStore = new ArmorStore();
    private Random random = new Random();

    private String currentDesc = "";

    public Equipment(Player player) {
        this.player = player;
    }

    public boolean addToEquipment(Item item) {
        for (int i = 1; i < 10; i++) {
            if (!items.containsKey(i) && !items.containsValue(item)) {
                items.put(i, item);
                player.addMessage(String.format("[INFO]: Added %s to equipment", item.getName()));
                return true;
            }
        }
        return false;
    }

    private boolean playerDontHaveThisItem(Item item) {
        return !player.getArmorList().contains(item) && !player.getWeapons().contains(item);
    }

    public void removeItemFromEquipment(Item itemToRemove) {
        if (items.values().removeIf(i -> i.equals(itemToRemove))) {
            player.addMessage(String.format("[INFO]: You've removed %s from equipment", itemToRemove.getName()));
        } else {
            player.addMessage(String.format("[WARN]: No item : %s in equipment", itemToRemove.getName()));
        }
    }
    public void removeItemFromEquipmentByKey(int key) {
        if (items.keySet().removeIf(k -> k == key)) {
            player.addMessage(String.format("[INFO]: You've removed item with id: %d from equipment", key));
        } else {
            player.addMessage(String.format("[WARN]: No item with id: %d in equipment", key));
        }
    }

    public void useSmallPotion() {
        if (smallPotions > 0) {
            smallPotions--;
            player.setHp(player.getHP()+30);
            player.addMessage("[INFO]: You've drunk Small Health Potion, health restored by +30");
        } else {
            player.addMessage("[WARN]: You are out of Small Health Potions!");
        }
    }

    public void useLargePotion() {
        if (largePotions > 0) {
            largePotions--;
            player.setHp(player.getHP()+50);
            player.addMessage("[INFO]: You've drunk Large Health Potion, health restored by +50");
        } else {
            player.addMessage("[WARN]: You are out of Large Health Potions!");
        }
    }

    public void addSmallPotion() {
        smallPotions++;
        player.addMessage("[LOOT]: One Small Potion added to equipment.");
    }

    public void addLargePotion() {
        largePotions++;
        player.addMessage("[LOOT]: One Large Potion added to equipment.");
    }

    public void uniqueTreasureDiscovery() {
        if (random() > 0.6) {
            addLargePotion();
            addSmallPotion();
        } else if (random() > 0.4 && random() < 0.6) {
            addSmallPotion();
        }
        if (random() > 0.2) {
            if (random.nextBoolean()) {
                drawRandomArmor();
            } else {
                drawRandomWeapon();
            }
        }
    }

    public void regularTreasureDiscovery() {
        if (random() > 0.9) {
            addLargePotion();
        } else if (random() > 0.8 && random() < 0.9) {
            addSmallPotion();
        }
        if (random() > 0.7) {
            if (random.nextBoolean()) {
                drawRandomArmor();
            } else {
                drawRandomWeapon();
            }
        }
    }

    private void drawRandomWeapon() {
        int key = random.nextInt(weaponStore.size() - 1) + 1;
        Weapon weapon = weaponStore.get(key);
        if (playerDontHaveThisItem(weapon)) {
            addToEquipment(weapon);
            player.addMessage("[LOOT]: Treasure loot " + weapon.getName() + "!");
        }
    }

    private void drawRandomArmor() {
        int key = random.nextInt(armorStore.size() - 1) + 1;
        Armor armor = armorStore.get(key);
        if (playerDontHaveThisItem(armor)) {
            addToEquipment(armor);
            player.addMessage("[LOOT]: Treasure loot " + armor.getName() + "!");
        }
    }

    public void setCurrentDesc(int key) {
        if (items.containsKey(key)) {
            currentDesc = items.get(key).getDescription();
        } else {
            player.addMessage(String.format("[WARN]: No item with id: %d in equipment", key));
        }

    }

    public String getCurrentDesc() {
        return currentDesc;
    }

    public Map<Integer, Weapon> getWeaponStore() {
        return weaponStore;
    }

    public ArmorStore getArmorStore() {
        return armorStore;
    }

    public int getSmallPotions() {
        return smallPotions;
    }

    public int getLargePotions() {
        return largePotions;
    }

    public Map<Integer, Item> getItems() {
        return items;
    }
}
