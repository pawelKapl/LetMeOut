package data.moveables;

public class Solider implements Player {

    private int x,y;
    private int hp;
    private String name;


    public Solider(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.hp = 100;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void moveUp() {
        x--;
    }

    @Override
    public void moveDown() {
        x++;
    }

    @Override
    public void moveLeft() {
        y--;
    }

    @Override
    public void moveRight() {
        y++;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getHP() {
        return hp;
    }

    private void analyseMove() {

    }

}
