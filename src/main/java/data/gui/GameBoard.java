package data.gui;

import data.equipment.Armor;
import data.equipment.Equipment;
import data.equipment.Item;
import data.equipment.Weapon;
import data.gameEngine.GameLogic;
import data.movables.enemies.Enemy;
import data.movables.playerClass.Player;
import data.other.Preferences;
import data.terrains.TerrainType;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Map;

@SuppressWarnings("serial")
public class GameBoard extends JPanel implements Updatable {

    private GameLogic game;
    private Player player;
    private TerrainType[][] location;


    public GameBoard(GameLogic game) {
        this.game = game;
        this.player = this.game.getPlayer();
        this.location = this.game.getTerrain().getMap();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Preferences.windowWidth, Preferences.windowHeight);
        printFrames(g);
        printLocation(g);
        printPlayer(g);
        printEnemies(g);
        printPlayerStatus(g);
        printEquipmentMenu(g);
        printFightLog(g);
        printEquipmentLog(g);
        printFogOfWar(g);

    }

    private void printFogOfWar(Graphics g) {
        boolean[][] fog = game.getFogOfWar().getFog();

        int dx = 35;
        int dy = 83;

        g.setColor(Color.BLACK);
        for (int i = 0; i < fog.length; i++) {
            for (int j = 0; j < fog[0].length; j++) {
                if(!fog[i][j]) {
                    g.fillRect(dx, dy, 12, 20);
                }
                dx += 12;
            }
            dy += 20;
            dx = 35;
        }
    }

    private void printPlayerStatus(Graphics g) {
        String profession = player.getClass().getSimpleName();

        Font font = new Font("legend", Font.BOLD, 15);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString("Location:  " + game.getTerrain().getName(), 35, 50);

        g.drawString("Player: " + player.getName() + "    Class: " + profession, 35, Preferences.windowHeight*2/3 + 50);
        g.setColor(Color.RED);
        g.drawString("hp: " + player.getHP(), 180 + player.getName().length()*7 + profession.length()*7,
                Preferences.windowHeight*2/3 + 50);
    }

    private void printFightLog(Graphics g) {
        LinkedList<String> fightLog = game.getFightUtil().getMessages();

        setLogFont(g);

        int y = Preferences.windowHeight*2/3 + 90;
        for (String s : fightLog) {

            if (s.startsWith("[FIGHT]")) {
                g.setColor(Color.YELLOW);
            } else if (s.startsWith("[EVENT]")) {
                g.setColor(Color.BLUE);
            } else if (s.startsWith("[ATTACK]")) {
                g.setColor(new Color(214, 30,30));
            }
            g.drawString(s, 35, y);
            y += 20;
        }
    }

    private void printEquipmentLog(Graphics g) {
        LinkedList<String> fightLog = game.getPlayer().getMessages();

        setLogFont(g);
        g.setColor(Color.WHITE);

        int y = Preferences.windowHeight*2/3 + 50;
        for (String s : fightLog) {

            if (s.startsWith("[INFO]")) {
                g.setColor(Color.WHITE);
            } else if (s.startsWith("[WARN]")) {
                g.setColor(new Color(214, 30,30));
            }
            g.drawString(s, 1070, y);
            y += 20;
        }
    }

    private void printLocation(Graphics g) {
        int dx = 35;
        int dy = 100;

        for (int i = 0; i < location.length; i++) {
            for (int j = 0; j < location[0].length; j++) {
                switch (location[i][j]) {
                    case WALL:
                        g.setColor(new Color(89, 159, 168));
                        g.drawString(location[i][j].getStamp(), dx, dy);
                        break;
                    case GROUND:
                        g.setColor(new Color(115, 124, 151));
                        g.drawString(location[i][j].getStamp(), dx, dy);
                        break;
                    case DOOR:
                        g.setColor(new Color(255, 0, 215));
                        g.drawString(location[i][j].getStamp(), dx, dy);
                        break;
                    case ITEM:
                    case UNIQUE_ITEM:
                        g.setColor(Color.YELLOW);
                        g.drawString(location[i][j].getStamp(), dx, dy);
                        break;
                    case FOREST:
                        g.setColor(new Color(24, 161, 24));
                        g.drawString(location[i][j].getStamp(), dx, dy);
                        break;
                }
                dx += 12;
            }
            dy += 20;
            dx = 35;
        }
    }

    private void printPlayer(Graphics g) {
        g.setColor(new Color(37, 29, 196));
        g.drawString("@", (player.getX()*12)+30, (player.getY()*20)+100);
    }

    private void printEnemies(Graphics g) {
        g.setColor(new Color(214, 30,30));
        for (Enemy e : game.getEnemies()) {
            g.drawString("k", (e.getX()*12)+35,(e.getY()*20)+100);
        }
    }

    private void printEquipmentMenu(Graphics g) {
        Equipment eq = player.getEquipment();
        Font font = new Font("equipment", Font.PLAIN, 12);
        g.setFont(font);
        g.setColor(Color.WHITE);
        int startWidth = Preferences.windowWidth*4/5 + 30;

        g.drawString("Equipment:", startWidth,255);
        startWidth += 5;
        g.setColor(new Color(210, 92, 92));
        g.drawString("Small Health Potions: " + eq.getSmallPotions() + "  <P>", startWidth, 285);
        g.setColor(new Color(160, 5, 5));
        g.drawString("Large Health Potions: " + eq.getLargePotions() + "  <L>", startWidth, 305);

        g.setColor(Color.WHITE);
        g.drawString("Items: ", startWidth, 345);

        startWidth += 10;
        int y = 365;
        if (eq.getItems().isEmpty()) {
            g.drawString("<Empty>", startWidth, y);
        } else {
            Map<Integer, Item> items = eq.getItems();
            for (int item : items.keySet()) {
                if (items.get(item) instanceof Weapon) {
                    g.setColor(new Color(35, 201, 26));
                } else if (items.get(item) instanceof Armor) {
                    g.setColor(new Color(24, 70, 201));
                }
                g.drawString(String.format("%d. %s", item, items.get(item).getName()), startWidth, y);
                y += 20;
            }
        }
    }

    private void printFrames(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawRect(10,10, Preferences.windowWidth*4/5, Preferences.windowHeight*2/3);
        g.drawRect(Preferences.windowWidth*4/5 + 20, 10, Preferences.windowWidth/5 - 50, Preferences.windowHeight*2/3);
        g.drawRect(10, Preferences.windowHeight*2/3 + 20, Preferences.windowWidth - 40, Preferences.windowHeight/3 - 110);
        g.setColor(new Color(45, 45, 45));
        g.drawRect(Preferences.windowWidth*4/5 + 30,265,250,400);
        g.drawRect(25, Preferences.windowHeight*2/3 + 70, 500,150);
        g.drawRect(1060,Preferences.windowHeight*2/3 + 30, 500, 200);

        g.setColor(Color.RED);
        g.drawString("DarkOnion", Preferences.windowWidth/2 - 30, Preferences.windowHeight - 60);
    }

    private void setLogFont(Graphics g) {
        Font font = new Font("legend", Font.PLAIN, 12);
        g.setFont(font);
    }


    @Override
    public void update() {
        super.repaint();
    }
}
