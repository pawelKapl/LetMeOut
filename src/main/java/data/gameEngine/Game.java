package data.gameEngine;

import data.moveables.Player;
import data.moveables.PlayerFactory;
import data.other.Preferences;
import data.terrains.Cave;
import data.terrains.Terrain;

public class Game {

    private Player player;
    private Terrain terrain;


    public Game() {
        terrain = new Cave("Planeta-Wojny", Preferences.mapWidth,Preferences.mapHeight);
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
}
