package tr.com.mek.bookshelf.exception;

import static tr.com.mek.bookshelf.exception.ErrorCode.ARGUMENT_NOT_VALID;

public class ModelArgumentNotValidException extends BookshelfException {

    private static final int CODE = ARGUMENT_NOT_VALID.getCode();

    public ModelArgumentNotValidException(String message) {
        super(message, CODE);
    }

    public ModelArgumentNotValidException(String message, Object data) {
        super(message, CODE, data);
    }
}
