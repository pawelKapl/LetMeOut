package data.equipment;

import data.movables.playerClass.Player;

import java.util.Map;
import java.util.TreeMap;

import static java.lang.StrictMath.random;

public class Equipment {

    private Player player;
    private int smallPotions = 2;
    private int largePotions = 1;
    private Map<Integer, Item> items = new TreeMap<>();

    private String currentDesc = "";

    public Equipment(Player player) {
        this.player = player;
    }

    public boolean addToEquipment(Item item) {
        for (int i = 1; i < 10; i++) {
            if (!items.containsKey(i) || items.containsValue(item)) {
                items.put(i, item);
                player.addMessage(String.format("[INFO]: Added %s to equipment", item.getName()));
                return true;
            }
        }
        return false;
    }

    public void removeItemFromEquipment(Item itemToRemove) {
        if (items.containsValue(itemToRemove)) {
            items.values().removeIf(i -> i.equals(itemToRemove));
            player.addMessage(String.format("[INFO]: You've removed %s from equipment", itemToRemove.getName()));
        } else {
            player.addMessage(String.format("[WARN]: No item : %s in equipment", itemToRemove.getName()));
        }
    }
    public void removeItemFromEquipmentByKey(int key) {
        if (items.containsKey(key)) {
            items.remove(key);
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
        player.addMessage("[INFO]: One Small Potion added to equipment.");
    }

    public void addLargePotion() {
        largePotions++;
        player.addMessage("[INFO]: One Large Potion added to equipment.");
    }

    public void uniqueTreasureDiscovery() {
        if (random() > 0.6) {
            player.getEquipment().addLargePotion();
            player.getEquipment().addSmallPotion();
        } else if (random() > 0.4 && random() < 0.6) {
            player.getEquipment().addSmallPotion();
        }
    }

    public void regularTreasureDiscovery() {
        if (random() > 0.9) {
            player.getEquipment().addLargePotion();
        } else if (random() > 0.8 && random() < 0.9) {
            player.getEquipment().addSmallPotion();
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
