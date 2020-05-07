package data.movables.playerClass;

import data.movables.Coords;
import data.movables.Movable;

public abstract class Player implements Movable {

    private Coords coords;
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

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getHP() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public abstract void getHit(int damage);

    public abstract int attack();
}
