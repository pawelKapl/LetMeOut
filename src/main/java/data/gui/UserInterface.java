package data.gui;

import data.gameEngine.Game;
import data.other.Preferences;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Container;
import java.awt.Dimension;

public class UserInterface implements Runnable {

    private JFrame frame;
    private GameBoard gameBoard;
    private Game game;

    public UserInterface(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        frame = new JFrame("Gwiezdna Flota");
        frame.setPreferredSize(new Dimension(Preferences.windowWidth, Preferences.windowHeight));
        createComponents(frame.getContentPane());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    private void createComponents(Container contentPane) {
        this.gameBoard = new GameBoard(game);
        contentPane.add(gameBoard);
        KeyboardController kc = new KeyboardController(game.getPlayer());
        kc.setUpdatable(gameBoard);
        frame.addKeyListener(kc);
    }

    public Updatable getUpdatable() {
        return this.gameBoard;
    }
}
