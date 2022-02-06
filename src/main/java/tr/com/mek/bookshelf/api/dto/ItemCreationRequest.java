package tr.com.mek.bookshelf.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.mek.bookshelf.domain.factory.ItemType;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class ItemCreationRequest {

    @NotNull(message = "Item type may not be null.")
    private ItemType itemType;

    @NotBlank(message = "Name may not be blank.")
    @Size(min = 3, max = 50, message = "Name size may be between 3 and 50 characters.")
    private String name;

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
