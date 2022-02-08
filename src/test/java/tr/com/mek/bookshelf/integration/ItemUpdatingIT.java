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
import tr.com.mek.bookshelf.domain.factory.ItemType;
import tr.com.mek.bookshelf.dto.ItemCreationRequest;
import tr.com.mek.bookshelf.dto.ItemUpdateRequest;
import tr.com.mek.bookshelf.exception.ErrorCode;
import tr.com.mek.bookshelf.integration.initializer.DatabaseInitializer;
import tr.com.mek.bookshelf.integration.object.TestItemRequest;
import tr.com.mek.bookshelf.repository.ItemRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tr.com.mek.bookshelf.integration.object.TestItemRequest.getTestItemCreationRequest;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = DatabaseInitializer.class)
@DisplayName("Item Updating Integration Test Cases")
public class ItemUpdatingIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ItemRepository itemRepository;

    private static final String testNameOneChar = "_";
    private static final String testNameFiftyChars = "testNameWith50Characters_testNameWith50Characters_";
    private static final String testNameFiftyOneChars = testNameFiftyChars + testNameOneChar;
    private static final String testNameHundredAndOneChars = testNameFiftyChars + testNameFiftyChars + testNameOneChar;
    private static final String argumentNotValidCode = String.valueOf(ErrorCode.ARGUMENT_NOT_VALID.getCode());
    private String testBookId;
    private String testMagazineId;

    @BeforeEach
    void init() throws Exception {
        String responseBook = postTestBookItem();
        testBookId = JsonPath.read(responseBook, "id");

        String responseMagazine = postTestMagazineItem();
        testMagazineId = JsonPath.read(responseMagazine, "id");
    }

    private String postTestBookItem() throws Exception {
        ItemCreationRequest request = getTestItemCreationRequest();
        String response = mockMvc.perform(post("/items")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse().getContentAsString();

        return response;
    }

    private String postTestMagazineItem() throws Exception {
        ItemCreationRequest request = getTestItemCreationRequest();
        request.setItemType(ItemType.MAGAZINE);
        String response = mockMvc.perform(post("/items")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse().getContentAsString();

        return response;
    }

    @Test
    @DisplayName("Updating Book Successfully")
    void updateBookSuccessfully() throws Exception {
        // given
        ItemUpdateRequest request = TestItemRequest.getTestItemUpdateRequest();

        // when
        ResultActions result = mockMvc.perform(put("/items/{id}", testBookId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());
        String response = result.andReturn().getResponse().getContentAsString();
        assertEquals(request.getAuthor(), JsonPath.read(response, "author"));
        assertEquals(request.getPublisher(), JsonPath.read(response, "publisher"));
        assertEquals(request.getPublicationYear(), JsonPath.read(response, "publicationYear.value"));
        assertEquals(request.isSigned(), JsonPath.read(response, "signed"));
    }

    @Test
    @DisplayName("Updating Magazine Successfully")
    void updateMagazineSuccessfully() throws Exception {
        // given
        ItemUpdateRequest request = TestItemRequest.getTestItemUpdateRequest();

        // when
        ResultActions result = mockMvc.perform(put("/items/" + testMagazineId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());
        String response = result.andReturn().getResponse().getContentAsString();
        assertEquals(request.getIssue(), JsonPath.read(response, "issue"));
    }

    /*
     *  Exception Tests
     */

    @Test
    @DisplayName("Throwing Exception While Updating Object If Author Name Size is Too Long")
    void throwExceptionIfAuthorNameSizeIsTooLong() throws Exception {
        // given
        ItemUpdateRequest request = TestItemRequest.getTestItemUpdateRequest();
        request.setAuthor(testNameFiftyOneChars); // author name with fifty one characters

        // when
        ResultActions result = mockMvc.perform(put("/items/" + testBookId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isBadRequest());
        String response = result.andReturn().getResponse().getContentAsString();
        assertTrue(response.contains(argumentNotValidCode));
        assertTrue(response.contains("Author name size may be up to 50 characters."));
    }

    @Test
    @DisplayName("Throwing Exception While Updating Object If Publisher Name Size is Too Long")
    void throwExceptionIfPublisherNameSizeIsTooLong() throws Exception {
        // given
        ItemUpdateRequest request = TestItemRequest.getTestItemUpdateRequest();
        request.setPublisher(testNameHundredAndOneChars); // publisher name with hundred and one characters

        // when
        ResultActions result = mockMvc.perform(put("/items/" + testBookId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isBadRequest());
        String response = result.andReturn().getResponse().getContentAsString();
        assertTrue(response.contains(argumentNotValidCode));
        assertTrue(response.contains("Publisher name size may be up to 100 characters."));
    }

    @Test
    @DisplayName("Throwing Exception While Updating Object If Publication Year is Not Appropriate")
    void throwExceptionIfPublicationYearIsEarlierThanFirstPublishedBook() throws Exception {
        // given
        ItemUpdateRequest request = TestItemRequest.getTestItemUpdateRequest();
        request.setPublicationYear(867); // an invalid publication year

        // when
        ResultActions result = mockMvc.perform(put("/items/" + testBookId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isBadRequest());
        String response = result.andReturn().getResponse().getContentAsString();
        assertTrue(response.contains(argumentNotValidCode));
        assertTrue(response.contains("Publication year must not be earlier" +
                " than the year of the first published book which is 868."));
    }

    @Test
    @DisplayName("Throwing Exception While Updating Object If Issue Value is Negative")
    void throwExceptionIfIssueValueIsNegative() throws Exception {
        // given
        ItemUpdateRequest request = TestItemRequest.getTestItemUpdateRequest();
        request.setIssue(-1);

        // when
        ResultActions result = mockMvc.perform(put("/items/" + testMagazineId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isBadRequest());
        String response = result.andReturn().getResponse().getContentAsString();
        assertTrue(response.contains(argumentNotValidCode));
        assertTrue(response.contains("Issue value may be positive."));
    }
}
