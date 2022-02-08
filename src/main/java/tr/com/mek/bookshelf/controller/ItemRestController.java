package tr.com.mek.bookshelf.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tr.com.mek.bookshelf.domain.model.item.Item;
import tr.com.mek.bookshelf.dto.ErrorResponse;
import tr.com.mek.bookshelf.dto.ItemCreationRequest;
import tr.com.mek.bookshelf.dto.ItemUpdateRequest;
import tr.com.mek.bookshelf.dto.LoanRequest;
import tr.com.mek.bookshelf.exception.ItemNotFoundException;
import tr.com.mek.bookshelf.exception.ModelArgumentNotValidException;
import tr.com.mek.bookshelf.service.CrudOperation;
import tr.com.mek.bookshelf.service.LoanOperation;
import tr.com.mek.bookshelf.service.OperationType;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemRestController {

    private final CrudOperation crudOperation;
    private final Map<String, LoanOperation> loanOperations;

    /*
     * Crud Operations
     */

    @GetMapping
    @Operation(description = "Get all items on database")
    public List<Item> getAllItems() {
        return crudOperation.getAllItems();
    }

    @GetMapping(value = "/{id}")
    @Operation(description = "Get item by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "ItemNotFoundException",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class)) }) })
    public Item getItem(@PathVariable("id") String itemId) throws ItemNotFoundException {
        return crudOperation.getItemById(itemId);
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
        return crudOperation.saveItem(request);
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
        return crudOperation.updateItem(itemId, request);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(description = "Delete item by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "ItemNotFoundException",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class)) }) })
    public void deleteItem(@PathVariable("id") String itemId) throws ItemNotFoundException {
        crudOperation.deleteItemById(itemId);
    }

    /*
     * Borrowing and Lending Operations
     */

    @PutMapping(value = "/{id}/loan")
    @Operation(description = "Borrow or lend the given item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ModelArgumentNotValidException (Custom)" +
                    " or MethodArgumentNotValidException (Spring Bind Exception)",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "ItemNotFoundException",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class)) }) })
    public Item borrowOrLendItem(@PathVariable("id") String itemId,
                           @RequestBody @Valid LoanRequest request) throws ModelArgumentNotValidException, ItemNotFoundException {
        return loanOperations.get(request.getType().toString()).doOperation(itemId, request);
    }

    @PutMapping(value = "/{id}/loan/undo")
    @Operation(description = "Undo the borrowing or lending operation for the given item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "ItemNotFoundException",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class)) }) })
    public Item undoLoanOperation(@PathVariable("id") String itemId, @RequestParam OperationType type) throws ItemNotFoundException {
        return loanOperations.get(type.toString()).undoOperation(itemId);
    }
}
