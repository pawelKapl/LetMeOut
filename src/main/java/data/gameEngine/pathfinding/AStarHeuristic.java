package data.gameEngine.pathfinding;

import data.movables.Movable;

public interface AStarHeuristic {

    float getCost(TileBasedMap map, Movable movable, int x, int y, int tx, int ty);

}
