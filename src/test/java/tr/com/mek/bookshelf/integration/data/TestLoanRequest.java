package tr.com.mek.bookshelf.integration.data;

import tr.com.mek.bookshelf.dto.LoanRequest;
import tr.com.mek.bookshelf.service.LoanOperationType;

public class TestLoanRequest {

    static final LoanOperationType type = LoanOperationType.BORROW;
    static final String name = "Test Person Name";
    static final String mobilePhone = "01234567890";
    static final String city = "Test City";

    public static LoanRequest getTestLoanRequest() {
        LoanRequest request = new LoanRequest();
        request.setType(type);
        request.setName(name);
        request.setMobilePhone(mobilePhone);
        request.setCity(city);

        return request;
    }
}
