package data.movables.playerClass;

public class Solider extends Player {


    public Solider(String name) {
        super(name);
        super.setDefense(5);
        super.setAttack(6);
    }

    @Override
    public int attack() {
        return getAttack();
    }
}
