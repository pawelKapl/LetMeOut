package data.gui.keyboardcontrollers;

import data.gameEngine.GameLogic;
import data.gui.Updatable;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Logger;

public class MovementKeyController implements KeyListener {

    private GameLogic game;
    private Updatable updatable;

    private static final Logger log = Logger.getLogger(MovementKeyController.class.toString());


    public MovementKeyController(GameLogic game, Updatable updatable) {
        log.info("Creating Movement Key Controller");
        this.game = game;
        this.updatable = updatable;
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
                performAttackIfExists(0);
                break;
            case KeyEvent.VK_V:
                performAttackIfExists(1);
                break;
            case KeyEvent.VK_B:
                performAttackIfExists(2);
                break;
            case KeyEvent.VK_Z:
                performSkillIfExists(0);
                break;
            case KeyEvent.VK_X:
                performSkillIfExists(1);
                break;
            case KeyEvent.VK_C:
                performSkillIfExists(2);
                break;
        }
    }

    public void performAttackIfExists(int i) {
        if (game.getPlayer().getSpecialAttacks().size() >= i + 1) {
            game.specialAttack(game.getPlayer().getSpecialAttacks().get(i));
        }
    }

    public void performSkillIfExists(int i) {
        if (game.getPlayer().getSpecialSkills().size() >= i + 1) {
            game.getPlayer().getSpecialSkills().get(i).performSkill(game.getPlayer());
            updatable.update();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
