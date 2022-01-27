package tr.com.mek.bookshelf.model.inventory;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
public class Magazine extends Inventory{

    private int issue;

    /**
     * This constructor method creates a magazine object with the required argument of super class.
     *
     * @param name
     */
    public Magazine(String name) {
        super(name);
    }
}
