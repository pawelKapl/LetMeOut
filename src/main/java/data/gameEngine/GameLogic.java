package data.gameEngine;

import data.gui.Updatable;
import data.moveables.Coords;
import data.moveables.PlayerFactory;
import data.moveables.enemies.Enemy;
import data.moveables.playerClass.Player;
import data.other.Preferences;
import data.terrains.Cave;
import data.terrains.Terrain;

import java.util.List;
import java.util.logging.Logger;

public class GameLogic {

    private Player player;
    private Terrain terrain;
    private Updatable updatable;
    private List<Enemy> enemies;

    private final Logger log = Logger.getLogger(this.getClass().toString());

    public GameLogic() {
        terrain = new Cave("Planeta-Wojny", Preferences.mapHeight,Preferences.mapWidth);
        setPlayer();
    }

    private void setPlayer() {
        PlayerFactory pf = new PlayerFactory(terrain);
        player = pf.buildPlayer("Recon", 1);
    }

    public Player getPlayer() {
        return player;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setUpdatable(Updatable updatable) {
        this.updatable = updatable;
    }

    public void movePlayer(int dx, int dy) {
        char[][] map = terrain.getMap();
        Coords coords = player.getCoords();
        int x = coords.getX() + dx;
        int y = coords.getY() + dy;

        if (x < 0 || y < 0 || x >= map[0].length || y >= map.length || map[y][x] == '#') {
            return;
        }

        if (map[y][x] == 'o' || map[y][x] == 'u') {
            checkTreasure();
        }

        coords.setX(x);
        coords.setY(y);
        updatable.update();
    }

    private void checkTreasure() {
        log.info("Checking for a treasure");
    }
}
