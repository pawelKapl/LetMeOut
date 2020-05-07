package data.movables.playerClass;

public class Solider extends Player {


    public Solider(String name) {
        super(name);
        super.setDefense(5);
        super.setAttack(10);
    }

    @Override
    public void getHit(int damage) {
        //todo
    }

    @Override
    public int attack() {
        return 0;
    }
}
