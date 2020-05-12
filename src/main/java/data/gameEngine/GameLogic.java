package data.gameEngine;

import data.gameEngine.pathfinding.AStarPathFinder;
import data.gameEngine.pathfinding.FPath;
import data.gameEngine.pathfinding.PathFinder;
import data.gameEngine.pathfinding.TileBasedMap;
import data.gameEngine.pathfinding.TileMapImpl;
import data.gui.Updatable;
import data.movables.Coords;
import data.movables.MovableFactory;
import data.movables.enemies.Enemy;
import data.movables.playerClass.Player;
import data.other.Preferences;
import data.terrains.Cave;
import data.terrains.Terrain;
import data.terrains.TerrainType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import static java.lang.StrictMath.random;

public class GameLogic {

    private Player player;
    private Terrain terrain;
    private Updatable updatable;
    private PathFinder pathFinder;
    private FogOfWar fogOfWar;
    private List<Enemy> enemies = new ArrayList<>();
    private MovableFactory movableFactory = new MovableFactory();
    private Random random = new Random();
    private FightUtil fightUtil;

    private final Logger log = Logger.getLogger(this.getClass().toString());

    public GameLogic() {
        log.info("Starting game logic engine!");
        prepareLocation();
    }

    private void prepareLocation() {
        log.info("Preparing Location Components");
        terrain = new Cave("Planeta-Wojny", Preferences.mapHeight,Preferences.mapWidth);
        setPlayer();
        addEnemies(Preferences.mapVolume/60);
        TileBasedMap tbm = new TileMapImpl(terrain.getMap(), enemies);
        pathFinder = new AStarPathFinder(tbm, 10, false);
        fogOfWar = new FogOfWar(Preferences.mapHeight, Preferences.mapWidth);
        fogOfWar.uncover(player.getCoords());
        fightUtil = new FightUtil(player, enemies);
        log.info("Preparation of Location Components ended successfully");
    }

    public void movePlayer(int dx, int dy) {
        int x = player.getX() + dx;
        int y = player.getY() + dy;

        if(!playerAliveCheck()) {
            restart();
            return;
        }

        if (occupiedByEnemy(x, y)) {
            fightUtil.attackEnemy(x, y);
            updatable.update();
            enemyTurn();
        }

        if (!isFree(x, y)) { return; }

        if (treasureDiscovery(x, y)) { return; }

        player.setX(x);
        player.setY(y);

        enemyTurn();
        fogOfWar.uncover(player.getCoords());
        updatable.update();
    }

    private boolean treasureDiscovery(int x, int y) {
        if (isTreasure(x, y)) {
            if (player.isLocked()) {
                updatable.update();
                return true;
            }
            if (terrain.getMap()[y][x] == TerrainType.UNIQUE_ITEM) {
                player.getEquipment().uniqueTreasureDiscovery();
            } else {
                player.getEquipment().regularTreasureDiscovery();
            }
            terrain.getMap()[y][x] = TerrainType.GROUND;
        }
        return false;
    }

    private void enemyTurn() {
        boolean playerInCombat = false;
        for (Enemy enemy : enemies) {
            if (fightUtil.tryAttackPlayer(enemy)) {
                playerInCombat = true;
            }

            if (moveEnemy(enemy)) {
                playerInCombat = true;
            }
        }
        if (!playerInCombat) {
            player.unlock();
        }
    }

    private boolean playerAliveCheck() {
        return player.getHP() > 0;
    }

    private void restart() {
        enemies.clear();
        prepareLocation();
        updatable.update();
    }

    private boolean moveEnemy(Enemy enemy) {
        if (playerWithinRange(enemy)) {
            log.info("Enemy has spotted player!");
            if (!chasePlayer(enemy)) {
                return false;
            }
        } else {
            if (random() > 0.8) {
                Coords newCoords = randomMove(enemy);
                if (isFree(newCoords.getX(), newCoords.getY())) {
                    enemy.setCoords(newCoords);
                }
            }
            return false;
        }
        return true;
    }

    private boolean chasePlayer(Enemy enemy) {
        FPath path = pathFinder.findPath(enemy, enemy.getX(), enemy.getY(), player.getX(), player.getY());
        if (path != null && isFree(path.getStep(1).getX(), path.getStep(1).getY())) {
                enemy.setCoords(path.getStep(1));
            player.lock();
            return true;
        }
        return false;
    }

    private boolean playerWithinRange(Enemy enemy) {
        int visionRadius = enemy.getVisionRadius();
        Coords temp = new Coords(0, 0);
        for (int i = -visionRadius; i <= visionRadius; i++) {
            temp.setY(enemy.getY() + i);
            for (int j = -visionRadius; j <= visionRadius; j++) {
                temp.setX(enemy.getX() + j);

                if (temp.equals(player.getCoords())) {
                    return true;
                }
            }
        }
        return false;
    }

    private Coords randomMove(Enemy enemy) {
        int x = enemy.getX();
        int y = enemy.getY();

            if (random() > 0.5) {
                if (random() > 0.5) {
                    x++;
                } else {
                    x--;
                }
            } else {
                if (random() > 0.5) {
                    y++;
                } else {
                    y--;
                }
            }
        return new Coords(x, y);
    }

    private void addEnemies(int number) {
        log.info("Adding enemies");
        for (int i = 0; i < number; i++) {
            Enemy enemy = movableFactory.buildEnemy(1);
            enemy.setCoords(setEnemyStartingPoint());
            enemies.add(enemy);
        }
        log.info("Adding Enemies completed successfully");
    }

    private void setPlayerStartingPoint() {
        log.info("Getting Starting Point for a new Player...");

        int y = terrain.getEntrance().getY();
        int x = terrain.getEntrance().getX();

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
        while (true) {
            int x = random.nextInt(Preferences.mapWidth - 1);
            int y = random.nextInt(Preferences.mapHeight - 1);

            if (isFree(x, y) && !isTreasure(x, y) && !isNearEntrance(x, y)) {
                for (int i = -3; i < 4; i++) {
                    for (int j = -3; j < 4; j++) {
                        int ndx = x + j;
                        int ndy = y + i;
                        if (isWithinMap(ndx, ndy) && isTreasure(ndx, ndy)) {
                            return new Coords(x, y);
                        }
                    }
                }
            }
        }
    }

    private boolean isNearEntrance(int x, int y) {
        int entranceY = terrain.getEntrance().getY();
        int entranceX = terrain.getEntrance().getX();

        for (int i = -3; i < 4; i++) {
            for (int j = -3; j < 4; j++) {
                int dx = entranceX + j;
                int dy = entranceY + i;
                if (dx == x && dy == y) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isFree(int x, int y) {
        return isWithinMap(x, y)
                && terrain.getMap()[y][x] != TerrainType.WALL
                && !occupiedByEnemy(x, y)
                && !occupiedByPlayer(x, y);
    }

    private boolean isWithinMap(int x, int y) {
        return x >= 0 && y >= 0
                && y < terrain.getMap().length
                && x < terrain.getMap()[0].length;
    }

    private boolean occupiedByPlayer(int x, int y) {
        return x == player.getX() && y == player.getY();
    }

    private boolean occupiedByEnemy(int x, int y) {
        boolean occupied = false;
        for(Enemy e : enemies) {
            if (e.getCoords().getX() == x && e.getCoords().getY() == y) {
                occupied = true;
                break;
            }
        }
        return occupied;
    }

    private boolean isTreasure(int x, int y) {
        return terrain.getMap()[y][x] == TerrainType.ITEM
                || terrain.getMap()[y][x] == TerrainType.UNIQUE_ITEM;
    }

    private void setPlayer() {
        player = movableFactory.buildPlayer("Recon", 1);
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

    public FogOfWar getFogOfWar() {
        return fogOfWar;
    }

    public FightUtil getFightUtil() {
        return fightUtil;
    }
}
