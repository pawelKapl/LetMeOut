package data.gameEngine.pathfinding;

import data.movables.Movable;


public interface PathFinder {

    FPath findPath(Movable movable, int sx, int sy, int tx, int ty);

}
