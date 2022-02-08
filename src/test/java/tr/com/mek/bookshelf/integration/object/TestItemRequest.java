package tr.com.mek.bookshelf.integration.object;

import tr.com.mek.bookshelf.domain.factory.ItemType;
import tr.com.mek.bookshelf.dto.ItemCreationRequest;
import tr.com.mek.bookshelf.dto.ItemUpdateRequest;

public class TestItemRequest {

    static final ItemType type = ItemType.BOOK;
    static final String name = "Test Item";
    static final String author = "Test Author";
    static final String publisher = "Test Publisher";
    static final Integer publicationYear = 2020;
    static final Integer issue = 1;
    static final boolean signed = false;

    public static ItemCreationRequest getTestItemCreationRequest() {
        ItemCreationRequest request = new ItemCreationRequest();
        request.setItemType(type);
        request.setName(name);
        request.setAuthor(author);
        request.setPublisher(publisher);
        request.setPublicationYear(publicationYear);
        request.setIssue(issue);
        request.setSigned(signed);

        return request;
    }

    public static ItemUpdateRequest getTestItemUpdateRequest() {
        ItemUpdateRequest request = new ItemUpdateRequest();
        request.setAuthor(author);
        request.setPublisher(publisher);
        request.setPublicationYear(publicationYear);
        request.setIssue(issue);
        request.setSigned(signed);

        return request;
    }
}
