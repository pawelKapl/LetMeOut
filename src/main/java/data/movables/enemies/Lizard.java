package data.movables.enemies;

import data.movables.Coords;

public class Lizard extends Enemy {


    public Lizard(Coords coords) {
        super(coords);
        super.setVisionRadius(3);
        super.setHP(40);
        super.setAttack(8);
        super.setDefense(6);
    }

    //+ some extra class specific skills to do
}
