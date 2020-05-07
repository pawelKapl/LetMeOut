package data.gameEngine.pathfinding;

import data.movables.Movable;
import data.movables.enemies.Enemy;

import java.util.List;

public class TileMapImpl implements TileBasedMap {

    private boolean[][] visited;
    private char[][] terrain;
    private List<Enemy> enemies;

    public TileMapImpl(char[][] terrain, List<Enemy> enemies) {
        this.terrain = terrain;
        this.visited = new boolean[terrain.length][terrain[0].length];
        this.enemies = enemies;
    }

    @Override
    public int getWidthInTiles() {
        return terrain[0].length;
    }

    @Override
    public int getHeightInTiles() {
        return terrain.length;
    }

    @Override
    public boolean blocked(Movable movable, int x, int y) {
        return terrain[y][x] == '#' || occupiedByUnit(x,y);
    }

    @Override
    public float getCost(Movable movable, int sx, int sy, int tx, int ty) {
        //for update to include forests as hard terrain
        return 1;
    }

    @Override
    public void pathFinderVisited(int x, int y) {
        visited[y][x] = true;
    }

    private boolean occupiedByUnit(int x, int y) {
        for (Enemy e : enemies) {
            if (e.getCoords().getX() == x && e.getCoords().getY() == y) {
                return true;
            }
        }
        return false;
    }
}
