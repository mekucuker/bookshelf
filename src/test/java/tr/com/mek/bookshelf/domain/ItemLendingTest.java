package tr.com.mek.bookshelf.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import tr.com.mek.bookshelf.domain.factory.ItemFactory;
import tr.com.mek.bookshelf.domain.factory.ItemType;
import tr.com.mek.bookshelf.domain.model.Item;
import tr.com.mek.bookshelf.domain.model.vo.Person;
import tr.com.mek.bookshelf.exception.BookshelfException;
import tr.com.mek.bookshelf.exception.ErrorCode;
import tr.com.mek.bookshelf.exception.ModelArgumentNotValidException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tr.com.mek.bookshelf.domain.data.DomainTestData.TEST_ITEM_NAME;
import static tr.com.mek.bookshelf.domain.data.TestPerson.getTestPerson;

@DisplayName("Item Lending Test Cases")
class ItemLendingTest {

    @ParameterizedTest
    @EnumSource(ItemType.class)
    @DisplayName("Lending An Item To The Lender Successfully")
    void lendItemToLenderSuccessfully(ItemType itemType) throws Exception {
        // given
        Item item = ItemFactory.create(itemType, TEST_ITEM_NAME);
        Person person = getTestPerson();

        // when
        item.lend(person);

        // then
        assertEquals(TEST_ITEM_NAME, item.getName());
        assertEquals(person.getName(), item.getPerson().getName());
        assertEquals(person.getMobilePhone(), item.getPerson().getMobilePhone());
        assertEquals(person.getCity(), item.getPerson().getCity());
        assertEquals(LocalDate.now(), item.getProcessingDate());
        assertEquals(false, item.isBorrowed());
        assertEquals(true, item.isLent());
    }

    @ParameterizedTest
    @EnumSource(ItemType.class)
    @DisplayName("Undo Lending Operation of An Item Successfully")
    void undoBorrowingOfItemSuccessfully(ItemType itemType) throws Exception {
        // given
        Item item = ItemFactory.create(itemType, TEST_ITEM_NAME);
        Person person = getTestPerson();
        item.lend(person);

        // when
        item.undoLending();

        // then
        assertEquals(null, item.getPerson());
        assertEquals(false, item.isLent());
    }

    /*
     *  Exception Tests
     */

    @ParameterizedTest
    @EnumSource(ItemType.class)
    @DisplayName("Throwing Exception If The Lender is Null While Lending An Item")
    void throwExceptionIfLenderIsNull(ItemType itemType) {
        // given
        Item item = ItemFactory.create(itemType, TEST_ITEM_NAME);

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
