package tr.com.mek.bookshelf.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class ItemUpdateRequest {

    @Size(max = 50, message = "Author name size may be up to 50 characters.")
    private String author;

    @Size(max = 100, message = "Publisher name size may be up to 100 characters.")
    private String publisher;

    @Min(value = 868, message = "Publication year must not be earlier" +
            " than the year of the first published book which is 868.")
    private Integer publicationYear;

    @Positive(message = "Issue value may be positive.")
    private Integer issue;

    private boolean signed;
}
