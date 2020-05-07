package data.movables;

public interface Movable {

    Coords getCoords();
    void setCoords(Coords coords);
    int getX();
    int getY();
    void setX(int x);
    void setY(int y);
}
