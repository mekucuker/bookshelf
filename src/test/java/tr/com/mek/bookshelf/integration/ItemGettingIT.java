package tr.com.mek.bookshelf.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import tr.com.mek.bookshelf.domain.model.Item;
import tr.com.mek.bookshelf.dto.ItemCreationRequest;
import tr.com.mek.bookshelf.integration.data.TestItemRequest;
import tr.com.mek.bookshelf.repository.ItemRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tr.com.mek.bookshelf.integration.data.IntegrationTestData.*;
import static tr.com.mek.bookshelf.integration.data.TestItem.getTestBookItem;

@IntegrationTest
@DisplayName("Item Getting Integration Test Cases")
class ItemGettingIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ItemRepository itemRepository;

    private String testId;
    private Item testItem;

    @BeforeEach
    void init() throws Exception {
        String response = postTestItem();
        testId = JsonPath.read(response, "id");

        testItem = getTestBookItem();
        testItem.setId(testId);
    }

    private String postTestItem() throws Exception {
        ItemCreationRequest request = TestItemRequest.getTestItemCreationRequest();
        String response = mockMvc.perform(post("/items")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse().getContentAsString();

        return response;
    }

    @Test
    @DisplayName("Getting All Items Successfully")
    void getAllItemsSuccessfully() throws Exception {
        // when
        ResultActions result = mockMvc.perform(get("/items")
                .contentType("application/json"));

        // then
        result.andExpect(status().isOk());
        String response = result.andReturn().getResponse().getContentAsString();
        assertTrue(response.contains(objectMapper.writeValueAsString(testItem)));
    }

    @Test
    @DisplayName("Getting Item By Id Successfully")
    void getItemByIdSuccessfully() throws Exception {
        // when
        ResultActions result = mockMvc.perform(get("/items/{id}", testId)
                .contentType("application/json"));

        // then
        result.andExpect(status().isOk());
        String response = result.andReturn().getResponse().getContentAsString();
        assertEquals(response, objectMapper.writeValueAsString(testItem));
    }

    /*
     *  Exception Tests
     */

    @Test
    @DisplayName("Throwing Exception While Getting Object If There is No Item With That Id")
    void throwExceptionIfItemNotFound() throws Exception {
        // when
        ResultActions result = mockMvc.perform(get("/items/{id}", TEST_WRONG_ID)
                .contentType("application/json"));

        // then
        result.andExpect(status().isNotFound());
        String response = result.andReturn().getResponse().getContentAsString();
        assertTrue(response.contains(ITEM_NOT_FOUND_CODE));
        assertTrue(response.contains(ITEM_NOT_FOUND_MESSAGE));
    }
}
