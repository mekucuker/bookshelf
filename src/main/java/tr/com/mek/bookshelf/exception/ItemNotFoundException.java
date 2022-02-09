package tr.com.mek.bookshelf.exception;

import static tr.com.mek.bookshelf.exception.ErrorCode.ITEM_NOT_FOUND;

public class ItemNotFoundException extends BookshelfException {

    private final static int CODE = ITEM_NOT_FOUND.getCode();
    private final static String MESSAGE = ITEM_NOT_FOUND.getDefaultMessage();

    public ItemNotFoundException(Object data) {
        super(MESSAGE, CODE, data);
    }
}
