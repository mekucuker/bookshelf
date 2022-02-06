package tr.com.mek.bookshelf.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import tr.com.mek.bookshelf.api.dto.ItemCreationRequest;
import tr.com.mek.bookshelf.api.dto.ItemUpdateRequest;
import tr.com.mek.bookshelf.domain.factory.ItemFactory;
import tr.com.mek.bookshelf.domain.factory.ItemType;
import tr.com.mek.bookshelf.domain.model.item.Item;
import tr.com.mek.bookshelf.domain.model.item.value.PublicationYear;
import tr.com.mek.bookshelf.exception.ItemNotFoundException;
import tr.com.mek.bookshelf.exception.ModelArgumentNotValidException;
import tr.com.mek.bookshelf.repository.ItemRepository;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ItemCrudOperation {

    private final ModelMapper modelMapper;
    private final ItemRepository itemRepository;

    /**
     * Method to get all item.
     *
     * @return Item List
     */
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    /**
     * Method to get item by id.
     *
     * @param itemId String
     * @return Item
     * @throws ItemNotFoundException
     */
    public Item getItemById(String itemId) throws ItemNotFoundException {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
    }

    /**
     * Method to create and save item to database.
     *
     * @param request ItemCreationRequest
     * @return Item
     * @throws ModelArgumentNotValidException
     */
    public Item saveItem(ItemCreationRequest request) throws ModelArgumentNotValidException {
        Item item = createNewItem(request);

        Integer requestPublicationYear = request.getPublicationYear();
        setPublicationYearOfItem(requestPublicationYear, item);

        modelMapper.map(request, item);

        return itemRepository.save(item);
    }

    private Item createNewItem(ItemCreationRequest request) {
        ItemType itemType = request.getItemType();
        String itemName = request.getName();

        return ItemFactory.create(itemType, itemName);
    }

    private void setPublicationYearOfItem(Integer year, Item item) throws ModelArgumentNotValidException {
        if (Objects.nonNull(year)) {
            PublicationYear publicationYear = PublicationYear.of(year);
            item.setPublicationYear(publicationYear);
        }
    }

    /**
     * Method to update the item on database.
     *
     * @param itemId String
     * @param request ItemUpdateRequest
     * @return Item
     * @throws ModelArgumentNotValidException
     * @throws ItemNotFoundException
     */
    public Item updateItem(String itemId, ItemUpdateRequest request) throws ModelArgumentNotValidException, ItemNotFoundException {
        Item item = getItemById(itemId);

        Integer requestPublicationYear = request.getPublicationYear();
        setPublicationYearOfItem(requestPublicationYear, item);

        modelMapper.map(request, item);

        return itemRepository.save(item);
    }

    /**
     * Method to delete item by id.
     *
     * @param itemId String
     * @throws ItemNotFoundException
     */
    public void deleteItemById(String itemId) throws ItemNotFoundException {
        // Checks if there is an item with the given id.
        itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));

        itemRepository.deleteById(itemId);
    }
}
