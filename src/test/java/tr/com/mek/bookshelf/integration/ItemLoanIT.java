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
import tr.com.mek.bookshelf.dto.LoanRequest;
import tr.com.mek.bookshelf.integration.data.TestItemRequest;
import tr.com.mek.bookshelf.integration.data.TestLoanRequest;
import tr.com.mek.bookshelf.repository.ItemRepository;
import tr.com.mek.bookshelf.service.LoanOperationType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tr.com.mek.bookshelf.integration.data.IntegrationTestData.*;
import static tr.com.mek.bookshelf.integration.data.TestItem.getTestBookItem;

@IntegrationTest
@DisplayName("Item Loan Integration Test Cases")
class ItemLoanIT {

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
    @DisplayName("Borrowing Item Successfully Integration Test")
    void borrowItemSuccessfully() throws Exception {
        // given
        LoanRequest request = TestLoanRequest.getTestLoanRequest();

        // when
        ResultActions result = mockMvc.perform(put("/items/{id}/loan", testId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());

        String response = result.andReturn().getResponse().getContentAsString();
        assertEquals(request.getName(), JsonPath.read(response, "person.name"));
        assertEquals(request.getMobilePhone(), JsonPath.read(response, "person.mobilePhone"));
        assertEquals(request.getCity(), JsonPath.read(response, "person.city"));
        assertEquals(true, JsonPath.read(response, "borrowed"));
    }

    @Test
    @DisplayName("Lending Item Successfully Integration Test")
    void lendItemSuccessfully() throws Exception {
        // given
        LoanRequest request = TestLoanRequest.getTestLoanRequest();
        request.setType(LoanOperationType.LEND);

        // when
        ResultActions result = mockMvc.perform(put("/items/{id}/loan", testId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());

        String response = result.andReturn().getResponse().getContentAsString();
        assertEquals(request.getName(), JsonPath.read(response, "person.name"));
        assertEquals(request.getMobilePhone(), JsonPath.read(response, "person.mobilePhone"));
        assertEquals(request.getCity(), JsonPath.read(response, "person.city"));
        assertEquals(true, JsonPath.read(response, "lent"));
    }

    @Test
    @DisplayName("Undo Borrowing Operation Successfully Integration Test")
    void undoBorrowingOperationSuccessfully() throws Exception {
        // given
        LoanOperationType type = LoanOperationType.BORROW;

        // when
        ResultActions result = mockMvc.perform(put("/items/{id}/loan/undo", testId)
                .param("type", type.toString())
                .contentType("application/json"));

        // then
        result.andExpect(status().isOk());
        String response = result.andReturn().getResponse().getContentAsString();
        assertEquals(false, JsonPath.read(response, "borrowed"));
    }

    @Test
    @DisplayName("Undo Lending Operation Successfully Integration Test")
    void undoLendingOperationSuccessfully() throws Exception {
        // given
        LoanOperationType type = LoanOperationType.LEND;

        // when
        ResultActions result = mockMvc.perform(put("/items/{id}/loan/undo", testId)
                .param("type", type.toString())
                .contentType("application/json"));

        // then
        result.andExpect(status().isOk());
        String response = result.andReturn().getResponse().getContentAsString();
        assertEquals(false, JsonPath.read(response, "lent"));
    }

    /*
     *  Exception Tests
     */

    @Test
    @DisplayName("Throwing Exception If Loan Operation Type is Null")
    void throwExceptionIfLoanOperationTypeIsNull() throws Exception {
        // given
        LoanRequest request = TestLoanRequest.getTestLoanRequest();
        request.setType(null);

        // when
        ResultActions result = mockMvc.perform(put("/items/{id}/loan", testId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isBadRequest());
        String response = result.andReturn().getResponse().getContentAsString();
        assertTrue(response.contains(ARGUMENT_NOT_VALID_CODE));
        assertTrue(response.contains("Loan operation type may not be null."));
    }

    @Test
    @DisplayName("Throwing Exception If Person Name is Null")
    void throwExceptionIfNameIsNull() throws Exception {
        // given
        LoanRequest request = TestLoanRequest.getTestLoanRequest();
        request.setName(null);

        // when
        ResultActions result = mockMvc.perform(put("/items/{id}/loan", testId)
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
    @DisplayName("Throwing Exception If Person Name Size is Not Appropriate")
    void throwExceptionIfNameSizeIsTooShortOrTooLong(String name) throws Exception {
        // given
        LoanRequest request = TestLoanRequest.getTestLoanRequest();
        request.setName(name);

        // when
        ResultActions result = mockMvc.perform(put("/items/{id}/loan", testId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isBadRequest());
        String response = result.andReturn().getResponse().getContentAsString();
        assertTrue(response.contains(ARGUMENT_NOT_VALID_CODE));
        assertTrue(result.andReturn().getResponse().getContentAsString().contains("Name size may be between 3 and 50 characters."));
    }

    @ParameterizedTest
    @ValueSource(strings = {TEST_PHONE_NUMBER_WITH_9_CHARACTERS, TEST_PHONE_NUMBER_WITH_12_CHARACTERS}) // phone numbers with nine and twelve characters
    @DisplayName("Throwing Exception If Mobile Phone Number Size is Not Appropriate")
    void throwExceptionIfMobilePhoneNumberSizeIsTooShortOrTooLong(String mobilePhone) throws Exception {
        // given
        LoanRequest request = TestLoanRequest.getTestLoanRequest();
        request.setMobilePhone(mobilePhone);

        // when
        ResultActions result = mockMvc.perform(put("/items/{id}/loan", testId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isBadRequest());
        String response = result.andReturn().getResponse().getContentAsString();
        assertTrue(response.contains(ARGUMENT_NOT_VALID_CODE));
        assertTrue(result.andReturn().getResponse().getContentAsString().contains("Mobile phone number size may be 10 or 11 characters."));
    }

    @ParameterizedTest
    @ValueSource(strings = {TEST_CITY_NAME_WITH_2_CHARACTERS, TEST_CITY_NAME_WITH_16_CHARACTERS}) // city names with two and sixteen characters
    @DisplayName("Throwing Exception If City Name Size is Not Appropriate")
    void throwExceptionIfCityNameSizeIsTooShortOrTooLong(String city) throws Exception {
        // given
        LoanRequest request = TestLoanRequest.getTestLoanRequest();
        request.setCity(city);

        // when
        ResultActions result = mockMvc.perform(put("/items/{id}/loan", testId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isBadRequest());
        String response = result.andReturn().getResponse().getContentAsString();
        assertTrue(response.contains(ARGUMENT_NOT_VALID_CODE));
        assertTrue(result.andReturn().getResponse().getContentAsString().contains("City name size may be between 3 and 15 characters."));
    }
}
