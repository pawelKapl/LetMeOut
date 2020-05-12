package data.gui.keyboardcontrollers;

import data.gameEngine.GameLogic;
import data.gui.Updatable;
import data.movables.playerClass.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Logger;

public class EquipmentKeyController implements KeyListener {

    private GameLogic game;
    private final Logger log = Logger.getLogger(this.getClass().toString());
    private Updatable updatable;


    public EquipmentKeyController(GameLogic game, Updatable updatable) {
        log.info("Creating Equipment Key Controller");
        this.game = game;
        this.updatable = updatable;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        Player player = game.getPlayer();

        if (keyCode > 48 && keyCode < 58 && e.isAltDown()) {
            if (locked(player)) { return; }
            player.getEquipment().removeItemFromEquipmentByKey(keyCode - 48);
            updatable.update();
            return;
        }

        if (keyCode > 48 && keyCode < 58 && e.isControlDown()) {
            player.getEquipment().setCurrentDesc(keyCode - 48);
            updatable.update();
            return;
        }

        ifWillToEquip(keyCode, player);

        switch (keyCode) {
            case KeyEvent.VK_P:
                player.getEquipment().useSmallPotion();
                updatable.update();
                break;
            case KeyEvent.VK_L:
                player.getEquipment().useLargePotion();
                updatable.update();
                break;
            case KeyEvent.VK_COMMA:
                if (locked(player)) { return; }
                player.removeArmorFromInventory();
                updatable.update();
                break;
            case KeyEvent.VK_PERIOD:
                if (locked(player)) { return; }
                player.removeWeaponFromInventory();
                updatable.update();
                break;
        }
    }

    private boolean locked(Player player) {
        if (player.isLocked()) {
            updatable.update();
            return true;
        }
        return false;
    }

    private void ifWillToEquip(int keyCode, Player player) {
        if (keyCode > 48 && keyCode < 58) {
            if (locked(player)) { return; }
            player.equip(keyCode - 48);
            updatable.update();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
