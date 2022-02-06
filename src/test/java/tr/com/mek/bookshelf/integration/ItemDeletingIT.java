package tr.com.mek.bookshelf.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import tr.com.mek.bookshelf.api.dto.ItemCreationRequest;
import tr.com.mek.bookshelf.exception.ErrorCode;
import tr.com.mek.bookshelf.integration.initializer.DatabaseInitializer;
import tr.com.mek.bookshelf.repository.ItemRepository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tr.com.mek.bookshelf.integration.object.TestRequest.getTestItemCreationRequest;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = DatabaseInitializer.class)
@DisplayName("Item Deleting Integration Test Cases")
public class ItemDeletingIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ItemRepository itemRepository;

    private static final String itemNotFoundCode = String.valueOf(ErrorCode.ITEM_NOT_FOUND.getCode());
    private static final String itemNotFoundMessage = String.valueOf(ErrorCode.ITEM_NOT_FOUND.getDefaultMessage());
    private String testId;
    private final String testWrongId = "wrongItemId";

    @BeforeEach
    void init() throws Exception {
        String response = postTestItem();
        testId = JsonPath.read(response, "id");
    }

    private String postTestItem() throws Exception {
        ItemCreationRequest request = getTestItemCreationRequest();
        String response = mockMvc.perform(post("/items")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse().getContentAsString();

        return response;
    }

    @Test
    @DisplayName("Deleting Item By Id Successfully")
    void deleteItemByIdSuccessfully() throws Exception {
        // when
        ResultActions result = mockMvc.perform(delete("/items/{id}", testId)
                .contentType("application/json"));

        // then
        result.andExpect(status().isOk());
        assertFalse(itemRepository.findById(testId).isPresent());
    }

    @Test
    @DisplayName("Throwing Exception While Deleting Object If There is No Item With That Id")
    void throwExceptionIfItemNotFound() throws Exception {
        // when
        ResultActions result = mockMvc.perform(delete("/items/{id}", testWrongId)
                .contentType("application/json"));

        // then
        result.andExpect(status().isNotFound());
        String response = result.andReturn().getResponse().getContentAsString();
        assertTrue(response.contains(itemNotFoundCode));
        assertTrue(response.contains(itemNotFoundMessage));
    }
}
