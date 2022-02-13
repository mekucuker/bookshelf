package tr.com.mek.bookshelf.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tr.com.mek.bookshelf.domain.model.vo.Person;
import tr.com.mek.bookshelf.domain.model.vo.PublicationYear;
import tr.com.mek.bookshelf.exception.ModelArgumentNotValidException;

import java.time.LocalDate;
import java.util.Objects;

@Data
@Document(collection = "items")
public abstract class Item {

    @Id
    private String id;
    private final String name;
    private PublicationYear publicationYear;
    private Person person;
    private LocalDate processingDate;
    private boolean borrowed;
    private boolean lent;

    /**
     * This method is used to borrow the item from someone.
     * Also the method validates that person argument is not null.
     *
     * @param person Person
     */
    public void borrow(Person person) throws ModelArgumentNotValidException {
        performTheProcess(person);
        this.borrowed = true;
    }

    /**
     * This method is used to lent the item to someone.
     * Also the method validates that person argument is not null.
     *
     * @param person Person
     */
    public void lend(Person person) throws ModelArgumentNotValidException {
        performTheProcess(person);
        this.lent = true;
    }

    private void performTheProcess(Person person) throws ModelArgumentNotValidException {
        if (Objects.isNull(person))
            throw new ModelArgumentNotValidException("Person may not be null.");

        this.processingDate = LocalDate.now();
        this.person = person;
    }

    /**
     * This method is used to undo the borrowing operation done before.
     */
    public void undoBorrowing() {
        this.person = null;
        this.borrowed = false;
    }

    /**
     * This method is used to undo the lending operation done before.
     */
    public void undoLending() {
        this.person = null;
        this.lent = false;
    }
}
