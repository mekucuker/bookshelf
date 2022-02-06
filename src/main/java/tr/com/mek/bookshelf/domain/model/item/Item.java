package tr.com.mek.bookshelf.domain.model.item;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tr.com.mek.bookshelf.domain.model.item.value.PublicationYear;
import tr.com.mek.bookshelf.domain.model.person.Person;
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
    private boolean loaned;

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
        this.loaned = true;
    }

    private void performTheProcess(Person person) throws ModelArgumentNotValidException {
        if (Objects.isNull(person))
            throw new ModelArgumentNotValidException("Person may not be null.");

        this.processingDate = LocalDate.now();
        this.person = person;
    }
}
