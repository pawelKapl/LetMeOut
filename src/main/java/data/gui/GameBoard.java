package data.gui;

import data.equipment.Equipment;
import data.gameEngine.GameLogic;
import data.movables.enemies.Enemy;
import data.movables.playerClass.Player;
import data.other.Preferences;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;

@SuppressWarnings("serial")
public class GameBoard extends JPanel implements Updatable {

    private GameLogic game;
    private Player player;
    private char[][] location;


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

        Font font = new Font("legend", Font.PLAIN, 12);
        g.setFont(font);
        g.setColor(Color.WHITE);

        int y = Preferences.windowHeight*2/3 + 90;
        for (String s : fightLog) {

            if (s.startsWith("Fight")) {
                g.setColor(Color.yellow);
            } else if (s.startsWith("Event")) {
                g.setColor(Color.BLUE);
            } else if (s.startsWith("Attack")) {
                g.setColor(new Color(214, 30,30));
            }
            g.drawString(s, 35, y);
            y += 20;
        }
    }

    private void printLocation(Graphics g) {
        int dx = 35;
        int dy = 100;

        for (int i = 0; i < location.length; i++) {
            for (int j = 0; j < location[0].length; j++) {
                switch (location[i][j]) {
                    case '#':
                        g.setColor(new Color(89, 159, 168));
                        g.drawString(Character.toString(location[i][j]), dx, dy);
                        break;
                    case '.':
                        g.setColor(new Color(115, 124, 151));
                        g.drawString(Character.toString(location[i][j]), dx, dy);
                        break;
                    case 'd':
                        g.setColor(Color.PINK);
                        g.drawString(Character.toString(location[i][j]), dx, dy);
                        break;
                    case 'o':
                    case 'Ϯ':
                        g.setColor(Color.YELLOW);
                        g.drawString(Character.toString(location[i][j]), dx, dy);
                        break;
                    case 'f':
                        g.setColor(new Color(24, 161, 24));
                        g.drawString(Character.toString(location[i][j]), dx, dy);
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
        g.drawString("Small Health Potions: " + eq.getSmallPotions() + "  <P>", startWidth, 285);
        g.drawString("Large Health Potions: " + eq.getLargePotions() + "  <L>", startWidth, 305);
        g.drawString("Items: ", startWidth, 345);
        startWidth += 10;
        int y = 365;
        if (eq.getItems().isEmpty()) {
            g.drawString("<Empty>", startWidth, y);
        } else {
            for (int item : eq.getItems().keySet()) {
                g.drawString(String.format("%d. %s", item, eq.getItems().get(item).getName()), startWidth, y);
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

        g.setColor(Color.RED);
        g.drawString("DarkOnion", Preferences.windowWidth/2 - 30, Preferences.windowHeight - 60);
    }

    @Override
    public void update() {
        super.repaint();
    }
}
