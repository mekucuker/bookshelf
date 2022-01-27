package tr.com.mek.bookshelf.exception;

public abstract class BaseException extends Exception {

    private int errorCode;

    public BaseException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
