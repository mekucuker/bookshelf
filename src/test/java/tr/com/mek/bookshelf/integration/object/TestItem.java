package tr.com.mek.bookshelf.integration.object;

import tr.com.mek.bookshelf.domain.factory.ItemFactory;
import tr.com.mek.bookshelf.domain.factory.ItemType;
import tr.com.mek.bookshelf.domain.model.item.Book;
import tr.com.mek.bookshelf.domain.model.item.Item;
import tr.com.mek.bookshelf.domain.model.item.value.PublicationYear;
import tr.com.mek.bookshelf.exception.ModelArgumentNotValidException;

public class TestItem {

    public static Item getTestBookItem() throws ModelArgumentNotValidException {
        Book book = (Book) ItemFactory.create(ItemType.BOOK, TestRequest.name);
        book.setAuthor(TestRequest.author);
        book.setPublisher(TestRequest.publisher);
        book.setSigned(TestRequest.signed);

        PublicationYear year = PublicationYear.of(TestRequest.publicationYear);
        book.setPublicationYear(year);

        return book;
    }
}
