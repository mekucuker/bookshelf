package tr.com.mek.bookshelf.integration.data;

import tr.com.mek.bookshelf.exception.ErrorCode;

public class IntegrationTestData {

    public static final String TEST_WRONG_ID = "wrongItemId";

    public static final String ITEM_NOT_FOUND_CODE = String.valueOf(ErrorCode.ITEM_NOT_FOUND.getCode());
    public static final String ITEM_NOT_FOUND_MESSAGE = String.valueOf(ErrorCode.ITEM_NOT_FOUND.getDefaultMessage());
    public static final String ARGUMENT_NOT_VALID_CODE = String.valueOf(ErrorCode.ARGUMENT_NOT_VALID.getCode());

    public static final String TEST_NAME_WITH_1_CHARACTER = "_";
    public static final String TEST_NAME_WITH_50_CHARACTERS = "testNameWith50Characters_testNameWith50Characters_";
    public static final String TEST_NAME_WITH_51_CHARACTERS = TEST_NAME_WITH_50_CHARACTERS + TEST_NAME_WITH_1_CHARACTER;
    public static final String TEST_NAME_WITH_101_CHARACTERS = TEST_NAME_WITH_50_CHARACTERS + TEST_NAME_WITH_50_CHARACTERS + TEST_NAME_WITH_1_CHARACTER;

    public static final String TEST_PHONE_NUMBER_WITH_9_CHARACTERS = "012345678";
    public static final String TEST_PHONE_NUMBER_WITH_12_CHARACTERS = "012345678901";

    public static final String TEST_CITY_NAME_WITH_2_CHARACTERS = "__";
    public static final String TEST_CITY_NAME_WITH_16_CHARACTERS = "_testCityWith16_";
}
