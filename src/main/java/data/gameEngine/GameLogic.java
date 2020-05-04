package data.gameEngine;

import data.gui.Updatable;
import data.moveables.Coords;
import data.moveables.EnemyFactory;
import data.moveables.PlayerFactory;
import data.moveables.enemies.Enemy;
import data.moveables.playerClass.Player;
import data.other.Preferences;
import data.terrains.Cave;
import data.terrains.Terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class GameLogic {

    private Player player;
    private Terrain terrain;
    private Updatable updatable;
    private List<Enemy> enemies = new ArrayList<>();
    private EnemyFactory enemyFactory;

    private final Logger log = Logger.getLogger(this.getClass().toString());

    public GameLogic() {
        this.enemyFactory = new EnemyFactory();
        terrain = new Cave("Planeta-Wojny", Preferences.mapHeight,Preferences.mapWidth);
        setPlayer();
        addEnemies(Preferences.mapVolume/70);
    }


    public void movePlayer(int dx, int dy) {
        char[][] map = terrain.getMap();
        Coords coords = player.getCoords();
        int x = coords.getX() + dx;
        int y = coords.getY() + dy;

        if (!isFree(x, y)) {
            return;
        }

        if (isTreasure(x, y)) {
            checkTreasure();
        }

        coords.setX(x);
        coords.setY(y);
        updatable.update();
    }

    private void addEnemies(int number) {
        for (int i = 0; i < number; i++) {
            Enemy enemy = enemyFactory.buildEnemy(1);
            enemy.setCoords(setEnemyStartingPoint());
            enemies.add(enemy);
        }
    }


    private void checkTreasure() {
        log.info("Checking for a treasure");
    }

    private void setPlayerStartingPoint() {
        log.info("Getting Starting Point for a new Player...");

        int y = terrain.getEntrances().keySet().iterator().next();
        int x = terrain.getEntrances().get(y);

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int nbx = x + j;
                int nby = y + i;
                if (i == 0 && j == 0) {
                } else if (isFree(nbx, nby)) {
                    player.setCoords(new Coords(nbx,nby));
                    return;
                }
            }
        }
        throw new RuntimeException("Error during calculation of player starting point");
    }

    private Coords setEnemyStartingPoint() {
        Random random = new Random();
        while (true) {
            int x = random.nextInt(Preferences.mapWidth - 1);
            int y = random.nextInt(Preferences.mapHeight - 1);

            if (isFree(x, y) && !isTreasure(x, y) && terrain.getMap()[y][x] != 'd') {
                for (int i = -3; i < 4; i++) {
                    for (int j = -3; j < 4; j++) {
                        int ndx = x + j;
                        int ndy = y + i;
                        if (isFree(ndx, ndy) && isTreasure(ndx, ndy)) {
                            return new Coords(x, y);
                        }
                    }
                }
            }
        }
    }

    private boolean isFree(int nbx, int nby) {
        return nbx >= 0 && nby >= 0
                && nby < terrain.getMap().length
                && nbx < terrain.getMap()[0].length
                && terrain.getMap()[nby][nbx] != '#'
                && !occupiedByEnemy(nbx, nby);
    }

    private boolean occupiedByEnemy(int nbx, int nby) {
        boolean occupied = false;
        for(Enemy e : enemies) {
            if (e.getCoords().getX() == nbx && e.getCoords().getY() == nby) {
                occupied = true;
                break;
            }
        }
        return occupied;
    }

    private boolean isTreasure(int nbx, int nby) {
        return terrain.getMap()[nby][nbx] == 'o'
                || terrain.getMap()[nby][nbx] == 'u';
    }

    private void setPlayer() {
        PlayerFactory pf = new PlayerFactory();
        player = pf.buildPlayer("Recon", 1);
        setPlayerStartingPoint();
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

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
