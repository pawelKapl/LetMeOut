package data.movables.player;

import data.equipment.armors.CasualArmor;
import data.equipment.weapons.CasualWeapon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    Solider player;
    CasualWeapon testWeapon = new CasualWeapon("testWeapon", "test1", 10);
    CasualArmor testArmor = new CasualArmor("testArmor", "test2", 3);

    @BeforeEach
    void setUp() {
        createPlayerForTests();
    }

    private void createPlayerForTests() {
        player = new Solider("test");
        player.getEquipment().addToEquipment(testArmor); //1
        player.getEquipment().addToEquipment(testWeapon); //2
    }

    @ParameterizedTest(name = "For a given sequence of xp addition: {0}," +
            " player will promote to {1} level")
    @CsvSource({
            "'0', 1",
            "'999', 1",
            "'1000', 2",
            "'1000, 1500', 3",
            "'1000, 1500, 2499', 3",
            "'5000', 4",
    })
    void gainExpTest(@ConvertWith(ArrayConverter.class) Long[] expSequence, int level) {
        for (int i = 0; i < expSequence.length; i++) {
            player.gainExp(expSequence[i]);
        }
        assertEquals(level , player.getLevel());
    }

    @Test
    @DisplayName("Checking if method is properly recognising type of item and its presence in equipment," +
            " and if it is deleting equipped item from equipment")
    void equipSuccessCaseTest() {
        //player is created already handling 1 piece of armor and weapon
        player.equip(1); //adding Armor
        assertEquals(2, player.getArmorList().size());
        assertEquals(1, player.getWeapons().size());
        assertEquals(1, player.getEquipment().getItems().size());
    }

    @Test
    @DisplayName("Checking if method will reject adding the very same object once again")
    void equipFailBecauseOfDuplicateCaseTest() {
        player.getWeapons().clear();
        player.getWeapons().add(testWeapon);
        player.equip(2); //adding the same weapon again
        assertEquals(1, player.getWeapons().size());

    }

    @Test
    @DisplayName("Checking if method will reject adding item that its not present in equipment")
    void equipFailBecauseLackOfItemInEquipmentCaseTest() {
        player.getEquipment().removeItemFromEquipmentByKey(2); //removing testWeapon from equipment
        player.equip(2); //adding the same weapon again
        assertEquals(1, player.getWeapons().size());
    }

    @Test
    @DisplayName("Checking if inventory will keep proper max amount of mounted weapons: 2")
    void equipWeapon() {
        //partly tested in equip method cases
        player.equipWeapon(testWeapon);
        player.equipWeapon(new CasualWeapon("testWeapon2", "test1", 10));
        assertEquals(2, player.getWeapons().size());
        assertEquals(1, player.getEquipment().getItems().size());

    }

    @Test
    void removeWeaponFromInventory() {
        player.removeWeaponFromInventory();
        assertEquals(0, player.getWeapons().size());
    }

    @Test
    @DisplayName("Checking if inventory will keep proper max amount of mounted armors: 3")
    void equipArmor() {
        //partly tested in equip method cases
        player.equipArmor(testArmor);
        player.equipArmor(new CasualArmor("testArmor2", "test1", 10));
        player.equipArmor(new CasualArmor("testArmor3", "test1", 10));
        assertEquals(3, player.getArmorList().size());
        assertEquals(1, player.getEquipment().getItems().size());
    }

    @Test
    void removeArmorFromInventory() {
        player.removeArmorFromInventory();
        assertEquals(0, player.getArmorList().size());

    }
}