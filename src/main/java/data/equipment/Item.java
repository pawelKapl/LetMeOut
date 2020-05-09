package data.equipment;

import data.movables.playerClass.Player;

public interface Item {

    void use(Player player);
    String getDescription();
    String getName();
    String getType();
}
