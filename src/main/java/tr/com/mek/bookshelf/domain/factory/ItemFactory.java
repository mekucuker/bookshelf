package tr.com.mek.bookshelf.domain.factory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import tr.com.mek.bookshelf.domain.model.Book;
import tr.com.mek.bookshelf.domain.model.Item;
import tr.com.mek.bookshelf.domain.model.Magazine;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemFactory {

    /**
     * Creates new item subtype object.
     *
     * @param itemType ItemType
     * @param itemName String
     * @return Item
     */
    public static Item create(ItemType itemType, String itemName) {
        return switch (itemType) {
            case BOOK -> new Book(itemName);
            case MAGAZINE -> new Magazine(itemName);
        };
    }
}
