package tr.com.mek.bookshelf.model.inventory.value;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Publisher {

    private final String value;

    /**
     * This static method creates a publisher value object.
     *
     * @param name
     * @return Publisher
     */
    public static Publisher of(String name) {
        return new Publisher(name);
    }
}
