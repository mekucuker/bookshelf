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

@DisplayName("Inventory Borrowing Test Cases")
public class InventoryBorrowingTest {

    private final String testPersonName = "Test Person";
    private final String testInventoryName = "Test Inventory";

    @ParameterizedTest
    @EnumSource(InventoryType.class)
    @DisplayName("Borrowing An Inventory From The Lender Successfully")
    void borrowInventoryFromLenderSuccessfully(InventoryType inventoryType) {
        // given
        Inventory inventory = InventoryFactory.create(inventoryType, testInventoryName);
        Person person = new Person(testPersonName);

        // when
        inventory.borrow(person);

        //then
        assertEquals(testInventoryName, inventory.getName());
        assertEquals(person, inventory.getPerson());
        assertEquals(LocalDate.now(), inventory.getProcessingDate());
        assertEquals(true, inventory.isBorrowed());
        assertEquals(false, inventory.isLoaned());
    }

    /*
     *  Exception Tests
     */

    @ParameterizedTest
    @EnumSource(InventoryType.class)
    @DisplayName("Throwing Exception If The Lender is Null While Borrowing An Inventory")
    void throwExceptionIfLenderIsNull(InventoryType inventoryType) {
        // given
        Inventory inventory = InventoryFactory.create(inventoryType, testInventoryName);

        // when
        Exception thrown = assertThrows(
                IllegalArgumentException.class,
                () -> inventory.borrow(null)
        );

        // then
        assertEquals("Person information is mandatory.", thrown.getMessage());
    }
}
