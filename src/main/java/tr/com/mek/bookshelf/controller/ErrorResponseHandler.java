package tr.com.mek.bookshelf.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tr.com.mek.bookshelf.dto.ErrorResponse;
import tr.com.mek.bookshelf.exception.BookshelfException;
import tr.com.mek.bookshelf.exception.ErrorCode;
import tr.com.mek.bookshelf.exception.ItemNotFoundException;

@RestControllerAdvice
public class ErrorResponseHandler {

    /**
     * Handles exceptions occur when item not found.
     *
     * @param exception ItemNotFoundException
     * @return ErrorResponse
     */
    @ExceptionHandler(value = ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleItemNotFoundException(ItemNotFoundException exception) {
        return new ErrorResponse(exception.getCode(), exception.getMessage(), exception.getData());
    }

    /**
     * Handles all custom exceptions of this application
     * if they are not handled specifically.
     *
     * @param exception BookshelfException
     * @return ErrorResponse
     */
    @ExceptionHandler(value = BookshelfException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBookshelfException(BookshelfException exception) {
        return new ErrorResponse(exception.getCode(), exception.getMessage(), exception.getData());
    }

    /**
     * Handles exception when any request argument is not be validated
     * by Spring Validation.
     *
     * @param exception MethodArgumentNotValidException
     * @return ErrorResponse
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return new ErrorResponse(ErrorCode.ARGUMENT_NOT_VALID.getCode(),
                exception.getBindingResult().getFieldError().getDefaultMessage(),
                exception.getBindingResult().getFieldError().getRejectedValue());
    }
}
