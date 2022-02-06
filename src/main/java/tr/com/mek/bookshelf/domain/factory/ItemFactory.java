package tr.com.mek.bookshelf.domain.factory;

import tr.com.mek.bookshelf.domain.model.item.Book;
import tr.com.mek.bookshelf.domain.model.item.Item;
import tr.com.mek.bookshelf.domain.model.item.Magazine;

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
