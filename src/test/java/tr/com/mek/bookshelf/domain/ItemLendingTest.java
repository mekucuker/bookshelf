package tr.com.mek.bookshelf.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import tr.com.mek.bookshelf.domain.factory.ItemFactory;
import tr.com.mek.bookshelf.domain.factory.ItemType;
import tr.com.mek.bookshelf.domain.model.item.Item;
import tr.com.mek.bookshelf.domain.model.person.Person;
import tr.com.mek.bookshelf.exception.BookshelfException;
import tr.com.mek.bookshelf.exception.ErrorCode;
import tr.com.mek.bookshelf.exception.ModelArgumentNotValidException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tr.com.mek.bookshelf.domain.object.TestPerson.getTestPerson;

@DisplayName("Item Lending Test Cases")
public class ItemLendingTest {

    private final String testItemName = "Test Item Name";

    @ParameterizedTest
    @EnumSource(ItemType.class)
    @DisplayName("Lending An Item To The Lender Successfully")
    void lendItemToLenderSuccessfully(ItemType itemType) throws Exception {
        // given
        Item item = ItemFactory.create(itemType, testItemName);
        Person person = getTestPerson();

        // when
        item.lend(person);

        // then
        assertEquals(testItemName, item.getName());
        assertEquals(person.getName(), item.getPerson().getName());
        assertEquals(person.getMobilePhone(), item.getPerson().getMobilePhone());
        assertEquals(person.getCity(), item.getPerson().getCity());
        assertEquals(LocalDate.now(), item.getProcessingDate());
        assertEquals(false, item.isBorrowed());
        assertEquals(true, item.isLoaned());
    }

    /*
     *  Exception Tests
     */

    @ParameterizedTest
    @EnumSource(ItemType.class)
    @DisplayName("Throwing Exception If The Lender is Null While Lending An Item")
    void throwExceptionIfLenderIsNull(ItemType itemType) {
        // given
        Item item = ItemFactory.create(itemType, testItemName);

        // when
        BookshelfException thrown = assertThrows(
                ModelArgumentNotValidException.class,
                () -> item.lend(null)
        );

        // then
        assertEquals(ErrorCode.ARGUMENT_NOT_VALID.getCode(), thrown.getCode());
        assertEquals("Person may not be null.", thrown.getMessage());
    }
}
