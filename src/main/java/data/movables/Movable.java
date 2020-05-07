package data.movables;

public interface Movable {

    Coords getCoords();
    int getHP();
    void setCoords(Coords coords);
    int getX();
    int getY();
    void setX(int x);
    void setY(int y);
}
