package data.equipment;

import data.movables.playerClass.Player;

import java.util.Map;
import java.util.TreeMap;

public class Equipment {


    private int smallPotions = 2;
    private int largePotions = 1;
    private Map<Integer, Item> items = new TreeMap<>();

    public Equipment() {
        addToEquipment(new BasicKnife());
    }

    public void addToEquipment(Item item) {
        for (int i = 1; i < 10; i++) {
            if (!items.containsValue(i)) {
                items.put(i, item);
                return;
            }
        }
    }

    public void removeFromEquipment(int number) {
        if (items.containsValue(number)) {
            items.remove(number);
        }
    }

    public void useSmallPotion(Player player) {
        if (smallPotions > 0) {
            smallPotions--;
            player.setHp(player.getHP()+30);
        }
    }

    public void useLargePotion(Player player) {
        if (largePotions > 0) {
            largePotions--;
            player.setHp(player.getHP()+50);
        }
    }

    public void addSmallPotion() {
        smallPotions++;
    }

    public void addLargePotion() {
        largePotions++;
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
