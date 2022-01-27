package tr.com.mek.bookshelf.model.inventory;

import lombok.Data;
import tr.com.mek.bookshelf.model.person.Person;

import java.time.LocalDate;
import java.util.Objects;

@Data
public abstract class Inventory {

    private final String name;
    private Person person;
    private LocalDate processingDate;
    private boolean borrowed;
    private boolean loaned;

    /**
     * This method is used to borrow the inventory from someone.
     *
     * @param person
     */
    public void borrow(Person person) {
        performTheProcess(person);
        this.borrowed = true;
    }

    /**
     * This method is used to lent the inventory to someone.
     *
     * @param person
     */
    public void lend(Person person) {
        performTheProcess(person);
        this.loaned = true;
    }

    private void performTheProcess(Person person) {
        if (Objects.isNull(person))
            throw new IllegalArgumentException("Person information is mandatory.");

        this.person = person;
        this.processingDate = LocalDate.now();
    }
}
