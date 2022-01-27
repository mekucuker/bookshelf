package tr.com.mek.bookshelf.factory;

import tr.com.mek.bookshelf.boundary.InventoryType;
import tr.com.mek.bookshelf.model.inventory.Book;
import tr.com.mek.bookshelf.model.inventory.Inventory;
import tr.com.mek.bookshelf.model.inventory.Magazine;

public class InventoryFactory {

    /**
     * This factory method creates new inventory subtype object.
     *
     * @param inventoryType
     * @param inventoryName
     * @return Inventory
     */
    public static Inventory create(InventoryType inventoryType, String inventoryName) {
        return switch (inventoryType) {
            case BOOK -> new Book(inventoryName);
            case MAGAZINE -> new Magazine(inventoryName);
            default -> throw new IllegalArgumentException("Inventory Factory argument is not appropriate.");
        };
    }
}
