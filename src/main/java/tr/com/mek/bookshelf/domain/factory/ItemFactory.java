package tr.com.mek.bookshelf.domain.factory;

import tr.com.mek.bookshelf.domain.model.Book;
import tr.com.mek.bookshelf.domain.model.Item;
import tr.com.mek.bookshelf.domain.model.Magazine;

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
