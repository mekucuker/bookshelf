package tr.com.mek.bookshelf.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.mek.bookshelf.service.OperationType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class LoanRequest {

    @NotNull(message = "Operation type may not be null.")
    private OperationType type;

    @NotBlank(message = "Name may not be blank.")
    @Size(min = 3, max = 50, message = "Name size may be between 3 and 50 characters.")
    private String name;

    @Size(min = 10, max = 11, message = "Mobile phone number size may be 10 or 11 characters.")
    private String mobilePhone;

    @Size(min = 3, max = 15, message = "City name size may be between 3 and 15 characters.")
    private String city;
}
