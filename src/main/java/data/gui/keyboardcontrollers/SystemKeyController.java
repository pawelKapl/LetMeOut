package data.gui.keyboardcontrollers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Logger;

public class SystemKeyController implements KeyListener {

    private static final Logger log = Logger.getLogger(SystemKeyController.class.toString());

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                log.info("Quitting program on user command.");
                System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
