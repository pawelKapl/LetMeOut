package data.movables.enemies;

import data.movables.Coords;

public class Lizard extends Enemy {


    public Lizard(Coords coords) {
        super(coords);
        super.setVisionRadius(3);
        super.setHP(15);
        super.setAttack(10);
        super.setDefense(2);
    }

    @Override
    public void getHit(int damage) {
        //todo
    }

    @Override
    public int attack() {
        //todo
        return 0;
    }
}
