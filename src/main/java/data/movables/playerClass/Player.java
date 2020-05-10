package data.movables.playerClass;

import data.equipment.Armor;
import data.equipment.Equipment;
import data.equipment.Item;
import data.equipment.Weapon;
import data.movables.Coords;
import data.movables.Movable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class Player implements Movable {

    private Equipment equipment;
    private List<Armor> armors = new ArrayList<>(4);
    private List<Weapon> weapons = new ArrayList<>(2);
    private LinkedList<String> messages = new LinkedList<>();
    private Coords coords;
    private int maxHp = 100;
    private int hp;
    private final String name;
    private int attack;
    private int defense;


    public Player(String name) {
        this.coords =  new Coords(0, 0);
        this.name = name;
        this.hp = 100;
    }

    public String getName() {
        return name;
    }

    @Override
    public Coords getCoords() {
        return coords;
    }

    @Override
    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    @Override
    public int getX() {
        return coords.getX();
    }

    @Override
    public int getY() {
        return coords.getY();
    }

    @Override
    public void setX(int x) {
        coords.setX(x);
    }

    @Override
    public void setY(int y) {
        coords.setY(y);
    }

    public void equip(int key) {
        if (equipment.getItems().containsKey(key)) {
            Item item = equipment.getItems().get(key);
            if (item instanceof Weapon) {
                equipWeapon((Weapon) item);
            } else {
                wearNewArmor((Armor) item);
            }
        }
    }

    public int getAttack() {
        int sumAttack = attack;
        for (Weapon weapon : weapons) {
            sumAttack += weapon.getBonusAttack();
        }
        return sumAttack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public void equipWeapon(Weapon weapon) {
        if (weapons.size() < 2) {
            weapons.add(weapon);
            equipment.removeItemFromEquipment(weapon);
            addMessage("[INFO]: Equipped new weapon: " + weapon.getName());
        } else {
            addMessage("[WARN]: You are handling already to many weapons! Limit = 2pcs");
        }
    }

    public void removeWeaponFromInventory() {
        if (!weapons.isEmpty()) {
            Weapon weapon = weapons.get(0);
            if (equipment.addToEquipment(weapon)) {
                weapons.remove(weapon);
                addMessage("[INFO]: Removed weapon: " + weapon.getName());
            } else {
                addMessage("[WARN]: Your equipment is already full, cannot move item!");
            }
        } else {
            addMessage("[WARN]: You are not handling any weapon");
        }
    }

    public int getDefense() {
        int sumDefense = defense;
        for (Armor armor : armors) {
            sumDefense += armor.getBonusDefense();
        }
        return sumDefense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public int getHP() {
        return hp;
    }

    public void setHp(int hp) {
        if (hp > maxHp) {
            this.hp = maxHp;
        } else {
            this.hp = hp;
        }
    }

    public List<Armor> getArmorList() {
        return armors;
    }

    public void wearNewArmor(Armor armor) {
        if (armors.size() < 4) {
            armors.add(armor);
            equipment.removeItemFromEquipment(armor);
            addMessage("[INFO]: Added new armor: " + armor.getName());
        } else {
            addMessage("[WARN]: You are wearing already to many items! Limit = 4pcs");
        }
    }

    public void removeArmorFromEquipment() {
        if (!armors.isEmpty()) {
            Armor armor = armors.get(0);
            if (equipment.addToEquipment(armor)) {
                armors.remove(armor);
                addMessage("[INFO]: Removed armor: " + armor.getName());
            } else {
                addMessage("[WARN]: Your equipment is already full, cannot move item!");
            }
        } else {
            addMessage("[WARN]: You are not wearing any armor");
        }
    }

    public void addMessage(String message) {
        if (messages.size() == 7) {
            messages.removeLast();
        }
        messages.push(message);
    }

    public LinkedList<String> getMessages() {
        return messages;
    }

    public void getHit(int damage) {
        hp -= damage;
    }

    public abstract int specialAttack();
}
