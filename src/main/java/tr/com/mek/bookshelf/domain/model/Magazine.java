package tr.com.mek.bookshelf.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper=true)
public class Magazine extends Item {

    private Integer issue;

    /**
     * Creates a magazine object with the required argument of super class.
     *
     * @param name String
     */
    public Magazine(String name) {
        super(name);
    }
}
