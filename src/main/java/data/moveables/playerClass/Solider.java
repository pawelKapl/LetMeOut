package data.moveables.playerClass;

import data.moveables.Coords;

public class Solider implements Player {

    private Coords coords;
    private int hp;
    private final String name;


    public Solider(Coords coords, String name) {
        this.coords = coords;
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
}
