import data.Location;
import data.LocationGoL;

public class Main {

    public static void main(String[] args) {
        Location empty = new LocationGoL("Planeta-Wojny",25,140);

        String[][] location = empty.getMap();

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
