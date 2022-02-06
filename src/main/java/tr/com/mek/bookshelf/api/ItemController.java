package tr.com.mek.bookshelf.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tr.com.mek.bookshelf.api.dto.ErrorResponse;
import tr.com.mek.bookshelf.api.dto.ItemCreationRequest;
import tr.com.mek.bookshelf.api.dto.ItemUpdateRequest;
import tr.com.mek.bookshelf.domain.model.item.Item;
import tr.com.mek.bookshelf.exception.ItemNotFoundException;
import tr.com.mek.bookshelf.exception.ModelArgumentNotValidException;
import tr.com.mek.bookshelf.service.ItemCrudOperation;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemCrudOperation itemCrudOperation;

    @GetMapping
    @Operation(description = "Get all items on database")
    public List<Item> getAllItems() {
        return itemCrudOperation.getAllItems();
    }

    @GetMapping(value = "/{id}")
    @Operation(description = "Get item by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "ItemNotFoundException",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class)) }) })
    public Item getItem(@PathVariable("id") String itemId) throws ItemNotFoundException {
        return itemCrudOperation.getItemById(itemId);
    }

    @PostMapping
    @Operation(description = "Create a new item")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", description = "ModelArgumentNotValidException (Custom)" +
                    " or MethodArgumentNotValidException (Spring Bind Exception)",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class)) }) })
    public Item createItem(@RequestBody @Valid ItemCreationRequest request) throws ModelArgumentNotValidException {
        return itemCrudOperation.saveItem(request);
    }

    @PutMapping(value = "/{id}")
    @Operation(description = "Update the given item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ModelArgumentNotValidException (Custom)" +
                    " or MethodArgumentNotValidException (Spring Bind Exception)",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "ItemNotFoundException",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class)) }) })
    public Item updateItem(@PathVariable("id") String itemId,
                           @RequestBody @Valid ItemUpdateRequest request) throws ModelArgumentNotValidException, ItemNotFoundException {
        return itemCrudOperation.updateItem(itemId, request);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(description = "Delete item by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "ItemNotFoundException",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class)) }) })
    public void deleteItem(@PathVariable("id") String itemId) throws ItemNotFoundException {
        itemCrudOperation.deleteItemById(itemId);
    }
}
