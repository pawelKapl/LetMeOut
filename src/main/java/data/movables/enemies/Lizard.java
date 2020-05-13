package data.movables.enemies;

import data.movables.Coords;

import java.io.Serializable;

public class Lizard extends Enemy implements Serializable {

    private static final long serialVersionUID = 3L;

    public Lizard(Coords coords) {
        super(coords);
        super.setVisionRadius(3);
        super.setHP(40);
        super.setAttack(10);
        super.setDefense(6);
    }

    //+ some extra class specific skills to do
}
