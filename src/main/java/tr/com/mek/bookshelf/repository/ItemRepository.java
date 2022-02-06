package tr.com.mek.bookshelf.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tr.com.mek.bookshelf.domain.model.item.Item;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {

}
