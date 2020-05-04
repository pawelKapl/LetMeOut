package data.moveables;

public interface Player {

    String getName();
    void moveUp();
    void moveDown();
    void moveLeft();
    void moveRight();
    int getX();
    int getY();
    int getHP();
}
