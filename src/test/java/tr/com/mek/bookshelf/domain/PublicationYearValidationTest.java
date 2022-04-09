package tr.com.mek.bookshelf.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tr.com.mek.bookshelf.domain.model.vo.PublicationYear;
import tr.com.mek.bookshelf.exception.BookshelfException;
import tr.com.mek.bookshelf.exception.ErrorCode;
import tr.com.mek.bookshelf.exception.ModelArgumentNotValidException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tr.com.mek.bookshelf.domain.data.DomainTestData.TEST_VALID_PUBLICATION_YEAR;

@DisplayName("Publication Year Validation Test Cases")
class PublicationYearValidationTest {

    private int testNextYear;

    @BeforeEach
    void setTestNextYear() {
        testNextYear = LocalDate.now().getYear() + 1;
    }

    @Test
    @DisplayName("Creating A Valid Publication Year Successfully")
    void createValidPublicationYearSuccessfully() throws Exception {
        // when
        PublicationYear publicationYear = PublicationYear.of(TEST_VALID_PUBLICATION_YEAR);

        // then
        assertEquals(TEST_VALID_PUBLICATION_YEAR, publicationYear.getValue());
    }

    /*
     *  Exception Tests
     */

    @Test
    @DisplayName("Throwing Exception If Given Publication Year is After The Current Year")
    void throwExceptionIfPublicationYearIsAfterTheCurrentYear() {
        // when
        BookshelfException thrown = assertThrows(
                ModelArgumentNotValidException.class,
                () -> PublicationYear.of(testNextYear)
        );

        // then
        assertEquals(ErrorCode.ARGUMENT_NOT_VALID.getCode(), thrown.getCode());
        assertEquals("Publication year must not be later than the current year.", thrown.getMessage());
        assertEquals(testNextYear, thrown.getData());
    }
}
