package data.movables.playerClass;

import data.equipment.Equipment;

public class Solider extends Player {

    public Solider(String name) {
        super(name);
        super.setDefense(8);
        super.setAttack(8);
        super.setEquipment(new Equipment(this));
    }

    //+ some extra class specific skills to do
}
