package data.gui;

import data.gameEngine.GameLogic;
import data.gui.keyboardcontrollers.EquipmentKeyController;
import data.gui.keyboardcontrollers.MovementKeyController;
import data.other.Preferences;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Container;
import java.awt.Dimension;

public class UserInterface implements Runnable {

    private JFrame frame;
    private GameBoard gameBoard;
    private GameLogic game;

    public UserInterface(GameLogic game) {
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
        frame.addKeyListener(new MovementKeyController(game));
        frame.addKeyListener(new EquipmentKeyController(game.getPlayer(), gameBoard));
    }

    public Updatable getUpdatable() {
        return this.gameBoard;
    }
}
