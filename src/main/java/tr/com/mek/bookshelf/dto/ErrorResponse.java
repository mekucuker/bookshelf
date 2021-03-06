package tr.com.mek.bookshelf.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private final int code;
    private final String message;
    private final Object data;
}
