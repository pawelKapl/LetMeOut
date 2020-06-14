package data.gui.keyboardcontrollers;

import data.gameEngine.GameLogic;
import data.gui.Updatable;
import data.movables.player.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Logger;

public class SkillsKeyController implements KeyListener {

    private GameLogic game;
    private Updatable updatable;

    private static final Logger log = Logger.getLogger(SkillsKeyController.class.toString());


    public SkillsKeyController(GameLogic game, Updatable updatable) {
        log.info("Creating Skills Key Controller");
        this.game = game;
        this.updatable = updatable;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        Player player = game.getPlayer();

        switch (keyCode) {
            case KeyEvent.VK_T:
                player.increaseAttack();
                updatable.update();
                break;
            case KeyEvent.VK_Y:
                player.increaseDefense();
                updatable.update();
                break;
            case KeyEvent.VK_U:
                player.increaseMaxHp();
                updatable.update();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
