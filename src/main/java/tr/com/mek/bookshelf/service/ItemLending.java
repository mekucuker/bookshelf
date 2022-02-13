package tr.com.mek.bookshelf.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.mek.bookshelf.domain.model.Item;
import tr.com.mek.bookshelf.domain.model.vo.Person;
import tr.com.mek.bookshelf.dto.LoanRequest;
import tr.com.mek.bookshelf.exception.ItemNotFoundException;
import tr.com.mek.bookshelf.exception.ModelArgumentNotValidException;
import tr.com.mek.bookshelf.repository.ItemRepository;

@Service("LEND")
@RequiredArgsConstructor
public class ItemLending implements LoanOperation {

    private final CrudOperation crudOperation;
    private final ItemRepository itemRepository;

    @Override
    public Item doOperation(String itemId, LoanRequest request) throws ItemNotFoundException, ModelArgumentNotValidException {
        Person person = generatePerson(request);

        Item item = crudOperation.getItemById(itemId);
        item.lend(person);
        return itemRepository.save(item);
    }

    @Override
    public Item undoOperation(String itemId) throws ItemNotFoundException {
        Item item = crudOperation.getItemById(itemId);
        item.undoLending();
        return itemRepository.save(item);
    }
}
