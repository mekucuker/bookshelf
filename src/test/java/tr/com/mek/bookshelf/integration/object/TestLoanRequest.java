package tr.com.mek.bookshelf.integration.object;

import tr.com.mek.bookshelf.dto.LoanRequest;
import tr.com.mek.bookshelf.service.OperationType;

public class TestLoanRequest {

    static final OperationType type = OperationType.BORROW;
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
