package data.other;

public class Preferences {

    public final static int windowWidth = 1600;
    public final static int windowHeight = 1000;

    public final static int[][] levelingMap = {

            {0,0,0,0,0},
            {0,0,0,0,0},
          //lvl exp  +a +d  +hp
            {2, 1000, 1, 1, 10},
            {3, 2500, 1, 1, 10},
            {4, 5000, 2, 1, 20},
            {5, 10000, 3, 2, 30},
            {6, 20000, 3, 2, 30}

    };

    public final static double[][] enemyStructure = {
            //{0:difficulty lvl, 1:lizards share, 2:predators share, 3:other monster share}
            {0, 0.8, 0.2},
            {1, 0.5, 0.5},
            {2, 0.2, 0.5, 0.3}
    };
}
