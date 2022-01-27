package tr.com.mek.bookshelf.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import tr.com.mek.bookshelf.boundary.InventoryType;
import tr.com.mek.bookshelf.factory.InventoryFactory;
import tr.com.mek.bookshelf.model.inventory.Inventory;
import tr.com.mek.bookshelf.model.person.Person;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Inventory Lending Test Cases")
public class InventoryLendingTest {

    private final String testPersonName = "Test Person";
    private final String testInventoryName = "Test Inventory";

    @ParameterizedTest
    @EnumSource(InventoryType.class)
    @DisplayName("Lending An Inventory To The Lender Successfully")
    void lendInventoryToLenderSuccessfully(InventoryType inventoryType) {
        // given
        Inventory inventory = InventoryFactory.create(inventoryType, testInventoryName);
        Person person = new Person(testPersonName);

        // when
        inventory.lend(person);

        //then
        assertEquals(testInventoryName, inventory.getName());
        assertEquals(person, inventory.getPerson());
        assertEquals(LocalDate.now(), inventory.getProcessingDate());
        assertEquals(false, inventory.isBorrowed());
        assertEquals(true, inventory.isLoaned());
    }

    /*
     *  Exception Tests
     */

    @ParameterizedTest
    @EnumSource(InventoryType.class)
    @DisplayName("Throwing Exception If The Lender is Null While Lending An Inventory")
    void throwExceptionIfLenderIsNull(InventoryType inventoryType) {
        // given
        Inventory inventory = InventoryFactory.create(inventoryType, testInventoryName);

        // when
        Exception thrown = assertThrows(
                IllegalArgumentException.class,
                () -> inventory.lend(null)
        );

        // then
        assertEquals("Person information is mandatory.", thrown.getMessage());
    }
}
