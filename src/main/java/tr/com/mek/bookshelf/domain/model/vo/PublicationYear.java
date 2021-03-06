package tr.com.mek.bookshelf.domain.model.vo;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import tr.com.mek.bookshelf.exception.ModelArgumentNotValidException;

import java.time.LocalDate;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PublicationYear {

    Integer value;

    /**
     * Creates a validated value object for publication year.
     * This method validates that publication year is not later than the current year.
     *
     * @param year int
     * @return PublicationYear
     */
    public static PublicationYear of(Integer year) throws ModelArgumentNotValidException {
        validatePublicationYear(year);
        return new PublicationYear(year);
    }

    private static void validatePublicationYear(Integer year) throws ModelArgumentNotValidException {
        int currentYear = LocalDate.now().getYear();
        // Validation that the publication year is not later than the current year.
        if (year > currentYear) {
            throw new ModelArgumentNotValidException("Publication year must not be later than the current year.", year);
        }
    }
}
