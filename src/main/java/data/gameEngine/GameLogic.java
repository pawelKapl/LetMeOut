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
import data.movables.player.Player;
import data.other.Preferences;
import data.terrains.Cave;
import data.terrains.CustomMap;
import data.terrains.Terrain;
import data.terrains.TerrainType;
import locations.Location;
import locations.LocationSaveUtil;
import locations.LocationsManager;

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
    private FightUtil fightUtil;
    private List<Enemy> enemies = new ArrayList<>();
    private MovableFactory movableFactory = new MovableFactory();
    private LocationsManager lm = new LocationsManager();
    private Random random = new Random();

    private static final Logger log = Logger.getLogger(GameLogic.class.toString());

    public GameLogic() {
        log.info("Starting game logic engine!");
        this.player = movableFactory.buildPlayer("Recon", 1);
        prepareLocation(lm.get("Planet Of War"));
    }

    private void prepareLocation(Location location) {
        log.info("Preparing Location Components");

        enemies.clear();
        if(!LocationSaveUtil.loadLocation(this, location.getName())) {

            if (!location.getLocationType().equals("CAVE")) {
                terrain = new CustomMap(location);
            } else {
                terrain = new Cave(location);
            }
            addEnemies(location.getEnemies(), location.getDifficulty());
            setPlayerStartingPoint();
            fogOfWar = new FogOfWar(location.getHeight(), location.getWidth());
            fogOfWar.uncover(player.getCoords());
        }
        enableLogic();
        log.info("Preparation of Location Components ended successfully");
    }

    private void enableLogic() {
        TileBasedMap tbm = new TileMapImpl(terrain.getMap(), enemies);
        pathFinder = new AStarPathFinder(tbm, 10, false);
        fightUtil = new FightUtil(player, enemies);
    }

    public void movePlayer(int dx, int dy) {
        int x = player.getX() + dx;
        int y = player.getY() + dy;

        if (occupiedByEnemy(x, y)) {
            fightUtil.attackEnemy(x, y);
            updatable.update();
        } else {
            if (checkForOtherEvents(x, y)) {
                return;
            }
            player.setX(x);
            player.setY(y);
        }
        enemyTurn();
        fogOfWar.uncover(player.getCoords());
        updatable.update();
    }

    private boolean checkForOtherEvents(int x, int y) {
        if (!isFree(x, y)) { return true; }
        if (moveToNextLocation(x, y)) { return true; }
        if (treasureDiscovery(x, y)) { return true; }
        isIsATrap(x, y);
        return false;
    }

    private void isIsATrap(int x, int y) {
        if (terrain.getMap()[y][x] == TerrainType.TRAP) {
            terrain.getMap()[y][x] = TerrainType.GROUND;
            if (random() > 0.4) {
                int dmg = (int)(player.getMaxHp()*0.15);
                player.getHit(dmg);
                player.addMessage("[INFO]: Trap Triggered! -" + dmg + "hp");
                updatable.update();
            } else {
                player.addMessage("[INFO]: Trap was broken/disarmed");
            }
        }
    }

    private boolean moveToNextLocation(int x, int y) {
        if (terrain.getMap()[y][x] == TerrainType.DOOR && !player.isLocked()) {
            LocationSaveUtil.saveLocation(terrain, enemies, fogOfWar, player.getCoords());
            String nextLocation = terrain.getInOuts().get(new Coords(x, y));
            prepareLocation(lm.get(nextLocation));
            updatable.update();
            return true;
        }
        return false;
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

    private void addEnemies(int number, int difficulty) {
        log.info("Adding enemies");
        double[] enemyShare = Preferences.enemyStructure[difficulty];

        for (int i = 1; i < enemyShare.length; i++) {
            int amount = (int)(enemyShare[i]*number);
            addEnemiesByRace(amount, i);
        }
        log.info("Adding Enemies completed successfully");
    }

    private void addEnemiesByRace(int amount, int race) {
        for (int i = 0; i < amount; i++) {
            Enemy enemy =  movableFactory.buildEnemy(race);
            enemy.setCoords(setEnemyStartingPoint());
            enemies.add(enemy);
        }
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
            int x = random.nextInt(terrain.getMap()[0].length - 1);
            int y = random.nextInt(terrain.getMap().length - 1);

            //if (isFree(x, y) && !isTreasure(x, y) && !isNearEntrance(x, y)) {
            if (terrain.getMap()[y][x] == TerrainType.GROUND && !occupiedByEnemy(x, y) && !isNearEntrance(x, y)) {
                for (int i = -4; i < 5; i++) {
                    for (int j = -4; j < 5; j++) {
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

    public Player getPlayer() {
        return player;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public void setUpdatable(Updatable updatable) {
        this.updatable = updatable;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public FogOfWar getFogOfWar() {
        return fogOfWar;
    }

    public void setFogOfWar(FogOfWar fogOfWar) {
        this.fogOfWar = fogOfWar;
    }

    public FightUtil getFightUtil() {
        return fightUtil;
    }
}
