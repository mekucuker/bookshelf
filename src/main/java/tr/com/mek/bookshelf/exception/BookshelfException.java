package tr.com.mek.bookshelf.exception;

import lombok.Getter;

@Getter
public abstract class BookshelfException extends Exception {

    private final int code;
    private Object data;

    protected BookshelfException(String message, int code) {
        super(message);
        this.code = code;
    }

    protected BookshelfException(String message, int code, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }
}
