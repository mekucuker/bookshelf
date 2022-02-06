package tr.com.mek.bookshelf.exception;

import static tr.com.mek.bookshelf.exception.ErrorCode.ARGUMENT_NOT_VALID;

public class ModelArgumentNotValidException extends BookshelfException {

    private final static int code = ARGUMENT_NOT_VALID.getCode();

    public ModelArgumentNotValidException(String message) {
        super(message, code);
    }

    public ModelArgumentNotValidException(String message, Object data) {
        super(message, code, data);
    }
}
