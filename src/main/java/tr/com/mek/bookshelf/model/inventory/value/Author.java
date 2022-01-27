package tr.com.mek.bookshelf.model.inventory.value;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Author {

    private final String value;

    /**
     * This static method creates an author value object.
     *
     * @param name
     * @return Author
     */
    public static Author of(String name) {
        return new Author(name);
    }
}
