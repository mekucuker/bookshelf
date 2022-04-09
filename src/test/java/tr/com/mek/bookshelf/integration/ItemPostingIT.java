package tr.com.mek.bookshelf.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import tr.com.mek.bookshelf.domain.model.Item;
import tr.com.mek.bookshelf.dto.ItemCreationRequest;
import tr.com.mek.bookshelf.integration.data.TestItemRequest;
import tr.com.mek.bookshelf.repository.ItemRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tr.com.mek.bookshelf.integration.data.IntegrationTestData.*;
import static tr.com.mek.bookshelf.integration.data.TestItem.getTestBookItem;

@IntegrationTest
@DisplayName("Item Posting Integration Test Cases")
class ItemPostingIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ItemRepository itemRepository;

    private Item testItem;

    @BeforeEach
    void setTestItem() throws Exception {
        testItem = getTestBookItem();
    }

    @Test
    @DisplayName("Creating Item Successfully")
    void createItemSuccessfully() throws Exception {
        // given
        ItemCreationRequest request = TestItemRequest.getTestItemCreationRequest();

        // when
        ResultActions result = mockMvc.perform(post("/items")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isCreated());

        String response = result.andReturn().getResponse().getContentAsString();
        String id = JsonPath.read(response, "id");
        assertNotNull(id);

        testItem.setId(id);
        assertEquals(response, objectMapper.writeValueAsString(testItem));
    }

    /*
     *  Exception Tests
     */

    @Test
    @DisplayName("Throwing Exception While Creating Object If Item Type is Null")
    void throwExceptionIfItemTypeIsNull() throws Exception {
        // given
        ItemCreationRequest request = TestItemRequest.getTestItemCreationRequest();
        request.setItemType(null);

        // when
        ResultActions result = mockMvc.perform(post("/items")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isBadRequest());
        String response = result.andReturn().getResponse().getContentAsString();
        assertTrue(response.contains(ARGUMENT_NOT_VALID_CODE));
        assertTrue(response.contains("Item type may not be null."));
    }

    @Test
    @DisplayName("Throwing Exception While Creating Object If Item Name is Null")
    void throwExceptionIfNameIsNull() throws Exception {
        // given
        ItemCreationRequest request = TestItemRequest.getTestItemCreationRequest();
        request.setName(null);

        // when
        ResultActions result = mockMvc.perform(post("/items")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isBadRequest());
        String response = result.andReturn().getResponse().getContentAsString();
        assertTrue(response.contains(ARGUMENT_NOT_VALID_CODE));
        assertTrue(response.contains("Name may not be blank."));
    }

    @ParameterizedTest
    @ValueSource(strings = {TEST_NAME_WITH_1_CHARACTER, TEST_NAME_WITH_51_CHARACTERS}) // names with one and fifty one characters
    @DisplayName("Throwing Exception While Creating Object If Name Size is Not Appropriate")
    void throwExceptionIfNameSizeIsTooShortOrTooLong(String name) throws Exception {
        // given
        ItemCreationRequest request = TestItemRequest.getTestItemCreationRequest();
        request.setName(name);

        // when
        ResultActions result = mockMvc.perform(post("/items")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isBadRequest());
        String response = result.andReturn().getResponse().getContentAsString();
        assertTrue(response.contains(ARGUMENT_NOT_VALID_CODE));
        assertTrue(result.andReturn().getResponse().getContentAsString().contains("Name size may be between 3 and 50 characters."));
    }

    @Test
    @DisplayName("Throwing Exception While Creating Object If Author Name Size is Too Long")
    void throwExceptionIfAuthorNameSizeIsTooLong() throws Exception {
        // given
        ItemCreationRequest request = TestItemRequest.getTestItemCreationRequest();
        request.setAuthor(TEST_NAME_WITH_51_CHARACTERS); // author name with fifty one characters

        // when
        ResultActions result = mockMvc.perform(post("/items")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isBadRequest());
        String response = result.andReturn().getResponse().getContentAsString();
        assertTrue(response.contains(ARGUMENT_NOT_VALID_CODE));
        assertTrue(result.andReturn().getResponse().getContentAsString().contains("Author name size may be up to 50 characters."));
    }

    @Test
    @DisplayName("Throwing Exception While Creating Object If Publisher Name Size is Too Long")
    void throwExceptionIfPublisherNameSizeIsTooLong() throws Exception {
        // given
        ItemCreationRequest request = TestItemRequest.getTestItemCreationRequest();
        request.setPublisher(TEST_NAME_WITH_101_CHARACTERS); // publisher name with hundred and one characters

        // when
        ResultActions result = mockMvc.perform(post("/items")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isBadRequest());
        String response = result.andReturn().getResponse().getContentAsString();
        assertTrue(response.contains(ARGUMENT_NOT_VALID_CODE));
        assertTrue(result.andReturn().getResponse().getContentAsString().contains("Publisher name size may be up to 100 characters."));
    }

    @Test
    @DisplayName("Throwing Exception While Creating Object If Publication Year is Not Appropriate")
    void throwExceptionIfPublicationYearIsEarlierThanFirstPublishedBook() throws Exception {
        // given
        ItemCreationRequest request = TestItemRequest.getTestItemCreationRequest();
        request.setPublicationYear(867); // an invalid publication year

        // when
        ResultActions result = mockMvc.perform(post("/items")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isBadRequest());
        String response = result.andReturn().getResponse().getContentAsString();
        assertTrue(response.contains(ARGUMENT_NOT_VALID_CODE));
        assertTrue(result.andReturn().getResponse().getContentAsString().contains("Publication year must not be earlier" +
                " than the year of the first published book which is 868."));
    }

    @Test
    @DisplayName("Throwing Exception While Creating Object If Issue Value is Negative")
    void throwExceptionIfIssueValueIsNegative() throws Exception {
        // given
        ItemCreationRequest request = TestItemRequest.getTestItemCreationRequest();
        request.setIssue(-1);

        // when
        ResultActions result = mockMvc.perform(post("/items")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isBadRequest());
        String response = result.andReturn().getResponse().getContentAsString();
        assertTrue(response.contains(ARGUMENT_NOT_VALID_CODE));
        assertTrue(result.andReturn().getResponse().getContentAsString().contains("Issue value may be positive."));
    }
}
