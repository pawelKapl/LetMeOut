package data.movables.enemies;

import data.movables.Coords;

import java.io.Serializable;

public final class Wherewolf extends Enemy implements Serializable {

    private static final long serialVersionUID = 3L;

    public Wherewolf(Coords coords) {
        super(coords);
        setVisionRadius(3);
        setHP(90);
        setAttack(15);
        setDefense(8);
        setExpReward(600);
    }

    //+ some extra class specific skills to do
}
