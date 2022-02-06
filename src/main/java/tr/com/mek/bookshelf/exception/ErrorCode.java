package tr.com.mek.bookshelf.exception;

/**
 * These enum values are used to tidy up the error codes and messages.
 */
public enum ErrorCode {

    ITEM_NOT_FOUND(1001, "Item not found."),
    ARGUMENT_NOT_VALID(1002, null);

    private int code;
    private String defaultMessage;

    private ErrorCode(int code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    public int getCode() {
        return code;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
