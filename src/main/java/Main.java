import data.Location;
import data.LocationGoL;

public class Main {

    public static void main(String[] args) {
        Location empty = new LocationGoL("Planeta-Wojny",40,200);

        String[][] location = empty.getMap();

        for (String[] y : location) {
            for (String x : y) {
                if (x.equals("#")) {
                    System.out.print("\033[0;36m" + x);
                } else if (x.equals(".")) {
                    System.out.print("\033[0m" + x);
                } else {
                    System.out.print("\033[0;33m" + x);
                }
            }
            System.out.println();
        }

    }


}
