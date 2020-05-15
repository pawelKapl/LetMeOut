package data.equipment;

import data.equipment.armors.CasualArmor;
import data.equipment.weapons.CasualWeapon;
import data.movables.player.Player;
import data.movables.player.Solider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EquipmentTest {

    Player player;
    Equipment equipment;
    CasualWeapon testWeapon = new CasualWeapon("testWeapon", "test1", 10);
    CasualArmor testArmor = new CasualArmor("testArmor", "test2", 3);


    @BeforeEach
    void setUp() {
        player = new Solider("test");
        player.setHp(10);
        equipment = new Equipment(player);
        for (int i = 1; i < 5; i++) {
            equipment.addToEquipment(equipment.getWeaponStore().get(i));
            equipment.addToEquipment(equipment.getArmorStore().get(i));
        }
        System.out.println("Eq size: " + equipment.getItems().size());
    }

    @Test
    @DisplayName("Checking if after creating a numeration gap in map, method will fill missing number with new item")
    void addToEquipmentSuccess() {
        equipment.getItems().remove(3);
        boolean test = equipment.addToEquipment(testArmor);
        assertEquals(true, test);
        assertEquals("testArmor", equipment.getItems().get(3).getName());
    }

    @Test
    @DisplayName("Checking if equipment is holding proper max amount of items")
    void addToEquipmentAndFailBecauseOfAlreadyFullEQ() {
        equipment.addToEquipment(testArmor);
        boolean test = equipment.addToEquipment(testWeapon);
        assertEquals(false, test);
    }

    @Test
    @DisplayName("Checking if equipment is rejecting items that it already contains")
    void addToEquipmentAndFailBecauseOfAlreadyContainedItem() {
        equipment.getItems().remove(1);
        equipment.addToEquipment(testArmor);
        boolean test = equipment.addToEquipment(testArmor);
        assertEquals(false, test);
    }

    @Test
    void removeItemFromEquipmentSuccess() {
        equipment.addToEquipment(testArmor);
        equipment.removeItemFromEquipment(testArmor);
        assertEquals(null, equipment.getItems().get(9));
    }

    @ParameterizedTest(name = "For a given key = {0}, equipment size should be equal to {1}, and message starting with {2}")
    @CsvSource({
            "0, 8, [WARN]:",
            "9, 8, [WARN]:",
            "3, 7, [INFO]:",
            "4, 7, [INFO]:"
    })
    void removeItemFromEquipmentByKey(int key, int size, String msg) {
        equipment.removeItemFromEquipmentByKey(key);
        assertEquals(size, equipment.getItems().size());
        assertEquals(msg, player.getMessages().peek().split(" ")[0]);
    }


    @Test
    void useSmallPotion() {
        equipment.useSmallPotion();
        assertEquals(1,equipment.getSmallPotions());
        assertEquals(40, player.getHP());
    }

    @Test
    void useLargePotion() {
        equipment.useLargePotion();
        assertEquals(0,equipment.getLargePotions());
        assertEquals(60, player.getHP());
    }
}