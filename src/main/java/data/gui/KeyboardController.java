package data.gui;

import data.moveables.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Logger;

public class KeyboardController implements KeyListener {

    private Player player;
    private final Logger log = Logger.getLogger(this.getClass().toString());
    private Updatable updatable;

    public KeyboardController(Player player) {
        this.player = player;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();
        log.info("Moved");

        switch (keyCode) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                player.moveUp();
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                player.moveDown();
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                player.moveLeft();
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                player.moveRight();
                break;
        }
        updatable.update();
    }

    public void setUpdatable(Updatable updatable) {
        this.updatable = updatable;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
