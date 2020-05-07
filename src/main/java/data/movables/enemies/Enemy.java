package data.movables.enemies;

import data.movables.Coords;
import data.movables.Movable;

public abstract class Enemy implements Movable {

    private static int nextNumber = 1;

    private Coords coords;
    private int hp;
    private int number;
    private int visionRadius;
    private int attack;
    private int defense;

    public Enemy(Coords coords) {
        this.coords = coords;
        number = nextNumber++;
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

    public int getHP() {
        return hp;
    }

    public void setHP(int hp) {
        this.hp = hp;
    }

    public int getNr() {
        return number;
    }
    public int getVisionRadius() {
        return visionRadius;
    }

    public void setVisionRadius(int visionRadius) {
        this.visionRadius = visionRadius;
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

    public abstract void getHit(int damage);

    public abstract int attack();
}
