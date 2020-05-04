package data.moveables.enemies;

import data.moveables.Coords;

public class Lizard implements Enemy {

    private Coords coords;
    private int hp;

    public Lizard(Coords coords) {
        this.coords = coords;
        this.hp = 15;
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
}
