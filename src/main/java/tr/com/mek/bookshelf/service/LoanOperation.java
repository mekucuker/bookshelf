package tr.com.mek.bookshelf.service;

import tr.com.mek.bookshelf.domain.model.item.Item;
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
}
