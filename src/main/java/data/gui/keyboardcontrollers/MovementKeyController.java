package data.gui.keyboardcontrollers;

import data.gameEngine.GameLogic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Logger;

public class MovementKeyController implements KeyListener {

    private GameLogic game;

    private static final Logger log = Logger.getLogger(MovementKeyController.class.toString());


    public MovementKeyController(GameLogic game) {
        log.info("Creating Movement Key Controller");
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

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
            case KeyEvent.VK_SPACE:
                game.specialAttack(game.getPlayer().getSpecialAttacks().get(0));
                break;
            case KeyEvent.VK_V:
                game.specialAttack(game.getPlayer().getSpecialAttacks().get(1));
                break;
            case KeyEvent.VK_B:
                game.specialAttack(game.getPlayer().getSpecialAttacks().get(2));
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
