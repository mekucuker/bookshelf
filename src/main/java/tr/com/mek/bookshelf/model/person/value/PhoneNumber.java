package tr.com.mek.bookshelf.model.person.value;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PhoneNumber {

    private final String value;

    /**
     * This static method creates a phone number value object.
     *
     * @param number
     * @return PhoneNumber
     */
    public static PhoneNumber of(String number) {
        return new PhoneNumber(number);
    }
}
