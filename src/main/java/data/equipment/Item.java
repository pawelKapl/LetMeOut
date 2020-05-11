package data.equipment;

public interface Item {

    String getDescription();
    void setDescription(String description);
    String getName();
    void setName(String name);

    boolean equals(Object o);
    int hashCode();
}
