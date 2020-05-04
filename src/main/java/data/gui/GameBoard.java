package data.gui;

import data.gameEngine.Game;
import data.moveables.Player;
import data.other.Preferences;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class GameBoard extends JPanel implements Updatable {

    private Game game;

    private final Logger log = Logger.getLogger(GameBoard.class.toString());

    public GameBoard(Game game) {
        this.game = game;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Preferences.windowWidth, Preferences.windowHeight);
        System.out.println("elo");
        printFrames(g);
        printLocation(g);
    }

    private void printLocation(Graphics g) {
        int dx = 35;
        int dy = 50;

        char[][] location = game.getTerrain().getMap();
        Player player = game.getPlayer();

        for (int i = 0; i < location.length; i++) {
            for (int j = 0; j < location[0].length; j++) {
                switch (location[i][j]) {
                    case '#':
                        g.setColor(Color.CYAN);
                        g.drawString(Character.toString(location[i][j]), dx, dy);
                        break;
                    case '.':
                        g.setColor(Color.WHITE);
                        g.drawString(Character.toString(location[i][j]), dx, dy);
                        break;
                    case 'd':
                        g.setColor(Color.PINK);
                        g.drawString(Character.toString(location[i][j]), dx, dy);
                    case 'o':
                        g.setColor(Color.YELLOW);
                        g.drawString(Character.toString(location[i][j]), dx, dy);
                        break;
                    case 'u':
                        g.setColor(Color.BLUE);
                        g.drawString(Character.toString(location[i][j]), dx, dy);
                    case 'f':
                        g.setColor(Color.GREEN);
                        g.drawString(Character.toString(location[i][j]), dx, dy);
                        break;
                }
                dx += 12;
            }
            dy += 20;
            dx = 35;
        }
        g.setColor(Color.BLUE);
        g.drawString("@", (player.getY()*12)+35, (player.getX()*20)+50);

    }

    private void printFrames(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawRect(10,10, Preferences.windowWidth*4/5, Preferences.windowHeight*2/3);
        g.drawRect(Preferences.windowWidth*4/5 + 20, 10, Preferences.windowWidth/5 - 50, Preferences.windowHeight*2/3);
        g.drawRect(10, Preferences.windowHeight*2/3 + 20, Preferences.windowWidth - 40, Preferences.windowHeight/3 - 120);
        g.setColor(Color.RED);
        g.drawString("DarkOnion", Preferences.windowWidth/2 - 30, Preferences.windowHeight - 70);
    }

    @Override
    public void update() {
        super.repaint();
    }
}
