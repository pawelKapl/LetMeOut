package data.gameEngine.pathfinding;

import data.movables.Coords;

import java.util.ArrayList;

public class FPath {

    private ArrayList<Coords> steps = new ArrayList();

    public int getLength() {
        return steps.size();
    }

    public Coords getStep(int index) {
        return steps.get(index);
    }

    public int getX(int index) {
        return getStep(index).getX();
    }

    public int getY(int index) {
        return getStep(index).getY();
    }

    public void appendStep(int x, int y) {
        steps.add(new Coords(x, y));
    }

    public void prependStep(int x, int y) {
        steps.add(0, new Coords(x, y));
    }

    public boolean contains(int x, int y) {
        return steps.contains(new Coords(x, y));
    }

    @Override
    public String toString() {
        return steps.toString();
    }
}
