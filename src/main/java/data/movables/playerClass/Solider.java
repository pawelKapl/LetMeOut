package data.movables.playerClass;

import data.movables.Coords;

public class Solider implements Player {

    private Coords coords;
    private int hp;
    private final String name;


    public Solider(String name) {
        this.coords =  new Coords(0, 0);
        this.name = name;
        this.hp = 100;
    }

    @Override
    public String getName() {
        return name;
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
}
