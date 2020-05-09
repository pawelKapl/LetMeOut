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
        this.player = player;
        this.updatable = updatable;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();
        log.info("Pressed key");

        switch (keyCode) {
            case KeyEvent.VK_P:
                player.getEquipment().useSmallPotion(player);
                updatable.update();
                break;
            case KeyEvent.VK_L:
                player.getEquipment().useLargePotion(player);
                updatable.update();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
