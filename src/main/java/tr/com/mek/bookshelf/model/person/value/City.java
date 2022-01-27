package tr.com.mek.bookshelf.model.person.value;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class City {

    private final String value;

    /**
     * This static method creates a city value object.
     *
     * @param name
     * @return City
     */
    public static City of(String name) {
        return new City(name);
    }
}
