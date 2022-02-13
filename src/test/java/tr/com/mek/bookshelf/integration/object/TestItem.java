package tr.com.mek.bookshelf.integration.object;

import tr.com.mek.bookshelf.domain.factory.ItemFactory;
import tr.com.mek.bookshelf.domain.factory.ItemType;
import tr.com.mek.bookshelf.domain.model.Book;
import tr.com.mek.bookshelf.domain.model.Item;
import tr.com.mek.bookshelf.domain.model.vo.PublicationYear;
import tr.com.mek.bookshelf.exception.ModelArgumentNotValidException;

public class TestItem {

    public static Item getTestBookItem() throws ModelArgumentNotValidException {
        Book book = (Book) ItemFactory.create(ItemType.BOOK, TestItemRequest.name);
        book.setAuthor(TestItemRequest.author);
        book.setPublisher(TestItemRequest.publisher);
        book.setSigned(TestItemRequest.signed);

        PublicationYear year = PublicationYear.of(TestItemRequest.publicationYear);
        book.setPublicationYear(year);

        return book;
    }
}
