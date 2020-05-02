import data.terrains.Cave;
import data.terrains.Terrain;

public class Main {

    public static void main(String[] args) {
        Terrain map = new Cave("Planeta-Wojny",25,140);

        printMap(map);
    }

    private static void printMap(Terrain map) {
        String[][] location = map.getMap();

        for (String[] y : location) {
            for (String x : y) {
                switch (x) {
                    case "#":
                        System.out.print("\033[1;36m" + x);
                        break;
                    case ".":
                        System.out.print("\033[0m" + x);
                        break;
                    case "+":
                        System.out.print("\033[1;35m" + x);
                        break;
                    default:
                        System.out.print("\u001B[92m" + x);
                        break;
                }
            }
            System.out.println();
        }
    }
}
