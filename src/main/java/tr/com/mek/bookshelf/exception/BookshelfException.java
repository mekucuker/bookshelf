package tr.com.mek.bookshelf.exception;

import lombok.Getter;

@Getter
public abstract class BookshelfException extends Exception {

    private int code;
    private Object data;

    public BookshelfException(String message, int code) {
        super(message);
        this.code = code;
    }

    public BookshelfException(String message, int code, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }
}
