package data.gameEngine;

import data.movables.Coords;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FogOfWarTest {

    FogOfWar fogOfWar;

    @BeforeEach
    void setUp() {
        fogOfWar = new FogOfWar(10,10);
    }

    @DisplayName("Checking if presence of Player uncovers proper points on fog map")
    @ParameterizedTest(name = "For given player position: x = {0}, y = {1} and coords: x = {2}, y = {3}, map should return {4}")
    @CsvSource({
            "1, 5, 1, 5, true",
            "1, 5, 1, 8, true",
            "1, 5, 0, 6, true",
            "1, 5, 4, 5, true",
            "1, 5, 3, 7, false",
            "1, 5, 0, 2, false",
            "1, 10, 0, 9, false",
            "1, 10, 1, 9, false",
       })
    void uncover(int xp, int yp, int x, int y, boolean uncovered) {
        fogOfWar.uncover(new Coords(xp, yp));
        assertEquals(uncovered, fogOfWar.getFog()[y][x]);
    }
}