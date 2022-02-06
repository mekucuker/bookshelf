package tr.com.mek.bookshelf.domain.model.item;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper=true)
public class Book extends Item {

    private String author;
    private String publisher;
    private boolean signed;

    /**
     * Creates a book object with the required argument of super class.
     *
     * @param name String
     */
    public Book(String name) {
        super(name);
    }
}
