package data.gui.keyboardcontrollers;

import data.gameEngine.GameLogic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Logger;

public class MovementKeyController implements KeyListener {

    private GameLogic game;
    private final Logger log = Logger.getLogger(this.getClass().toString());


    public MovementKeyController(GameLogic game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();
        log.info("Pressed key");

        switch (keyCode) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                game.movePlayer(0, -1);
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                game.movePlayer(0, 1);
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                game.movePlayer(-1, 0);
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                game.movePlayer(1, 0);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
