package data.gui.keyboardcontrollers;

import data.gui.Updatable;
import data.movables.playerClass.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Logger;

public class EquipmentKeyController implements KeyListener {

    private Player player;
    private final Logger log = Logger.getLogger(this.getClass().toString());
    private Updatable updatable;


    public EquipmentKeyController(Player player, Updatable updatable) {
        log.info("Creating Equipment Key Controller");
        this.player = player;
        this.updatable = updatable;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode > 48 && keyCode < 58 && e.isAltDown()) {
            if (locked()) { return; }
            player.getEquipment().removeItemFromEquipmentByKey(keyCode - 48);
            updatable.update();
            return;
        }

        ifWillToEquip(keyCode);

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
                if (locked()) { return; }
                player.removeArmorFromInventory();
                updatable.update();
                break;
            case KeyEvent.VK_PERIOD:
                if (locked()) { return; }
                player.removeWeaponFromInventory();
                updatable.update();
                break;
        }
    }

    private boolean locked() {
        if (player.isLocked()) {
            updatable.update();
            return true;
        }
        return false;
    }

    private void ifWillToEquip(int keyCode) {
        if (keyCode > 48 && keyCode < 58) {
            if (locked()) { return; }
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
