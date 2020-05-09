package data.movables.playerClass;

public class Solider extends Player {

    public Solider(String name) {
        super(name);
        super.setDefense(8);
        super.setAttack(8);
    }

    @Override
    public int attack() {
        return getAttack();
    }
}
