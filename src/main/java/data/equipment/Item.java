package data.equipment;

public interface Item {

    String getDescription();
    String getName();
    boolean equals(Object o);
    int hashCode();
}
