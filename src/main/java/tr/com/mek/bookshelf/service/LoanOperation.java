package tr.com.mek.bookshelf.service;

import tr.com.mek.bookshelf.domain.model.Item;
import tr.com.mek.bookshelf.domain.model.vo.Person;
import tr.com.mek.bookshelf.dto.LoanRequest;
import tr.com.mek.bookshelf.exception.ItemNotFoundException;
import tr.com.mek.bookshelf.exception.ModelArgumentNotValidException;

public interface LoanOperation {

    /**
     * Interface method to borrow or lend an item.
     * This operation is done with person information.
     *
     * @param itemId String
     * @param request LoanRequest
     * @return Item
     * @throws ItemNotFoundException
     * @throws ModelArgumentNotValidException
     */
    Item doOperation(String itemId, LoanRequest request) throws ItemNotFoundException, ModelArgumentNotValidException;

    /**
     * Interface method to undo borrowing or lending operation on an item.
     * Sets the person information of the item to null.
     *
     * @param itemId String
     * @return Item
     * @throws ItemNotFoundException
     */
    Item undoOperation(String itemId) throws ItemNotFoundException;

    /**
     * Default method to generate a person object by the given information in the request.
     *
     * @param request LoanRequest
     * @return Person
     */
    default Person generatePerson(LoanRequest request) {
        String name = request.getName();
        String mobilePhone = request.getMobilePhone();
        String city = request.getCity();

        return Person.of(name, mobilePhone, city);
    }
}
