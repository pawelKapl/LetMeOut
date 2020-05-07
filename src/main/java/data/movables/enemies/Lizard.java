package data.movables.enemies;

import data.movables.Coords;

public class Lizard implements Enemy {

    private static int nextNumber = 1;

    private Coords coords;
    private int hp;
    private int number;

    public Lizard(Coords coords) {
        this.coords = coords;
        this.hp = 15;
        number = nextNumber++;

    }

    @Override
    public Coords getCoords() {
        return coords;
    }

    @Override
    public int getHP() {
        return hp;
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

    @Override
    public int getNr() {
        return number;
    }
}
