package data.movables.enemies;

import data.movables.Coords;

import java.io.Serializable;

public final class Lizard extends Enemy implements Serializable {

    private static final long serialVersionUID = 3L;

    public Lizard(Coords coords) {
        super(coords);
        setVisionRadius(3);
        setHP(40);
        setAttack(10);
        setDefense(6);
        setExpReward(150);
    }

    //+ some extra class specific skills to do
}
