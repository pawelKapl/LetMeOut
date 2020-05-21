package data.gameEngine;

import data.movables.Coords;

import java.util.Optional;
import java.util.function.BiPredicate;

public interface IsNear {

    static boolean isNear(Coords first, Coords second, int radius) {
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                int dx = first.getX() + j;
                int dy = first.getY() + i;
                if (second.getX() == dx && second.getY() == dy) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean isNear(int x1, int y1, int x2, int y2, int radius) {
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                int dx = x1 + j;
                int dy = y1 + i;
                if (x2 == dx && y2 == dy) {
                    return true;
                }
            }
        }
        return false;
    }

    static Optional<Coords> convenientCoords(int x, int y, int radius, BiPredicate predicate) {
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                int dx = x + j;
                int dy = y + i;
                if (predicate.test(dx, dy)) {
                    return Optional.ofNullable(new Coords(dx, dy));
                }
            }
        }
        return Optional.empty();
    }
}
