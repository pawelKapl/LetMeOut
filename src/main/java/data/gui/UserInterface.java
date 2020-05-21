package data.gui;

import data.gameEngine.GameLogic;
import data.gui.keyboardcontrollers.EquipmentKeyController;
import data.gui.keyboardcontrollers.MovementKeyController;
import data.gui.keyboardcontrollers.SystemKeyController;
import data.other.Preferences;

import javax.swing.ImageIcon;
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
        frame.setPreferredSize(new Dimension(Preferences.windowWidth-20, Preferences.windowHeight-40));
        createComponents(frame.getContentPane());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setUndecorated (true);
        frame.dispose();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createComponents(Container contentPane) {
        this.gameBoard = new GameBoard(game);
        contentPane.add(gameBoard);
        ImageIcon img = new ImageIcon("src/resources/icon.png");
        frame.setIconImage(img.getImage());
        frame.addKeyListener(new MovementKeyController(game));
        frame.addKeyListener(new EquipmentKeyController(game, gameBoard));
        frame.addKeyListener(new SystemKeyController());
    }


    public Updatable getUpdatable() {
        return this.gameBoard;
    }
}
