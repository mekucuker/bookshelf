package tr.com.mek.bookshelf.model.inventory;

import lombok.EqualsAndHashCode;
import tr.com.mek.bookshelf.model.inventory.value.Author;
import tr.com.mek.bookshelf.model.inventory.value.Publisher;

@EqualsAndHashCode(callSuper=true)
public class Book extends Inventory{

    private Author author;
    private Publisher publisher;
    private int publicationYear;
    private boolean signed;

    /**
     * This constructor method creates a book object with the required argument of super class.
     *
     * @param name
     */
    public Book(String name) {
        super(name);
    }
}
