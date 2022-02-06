package tr.com.mek.bookshelf.domain.object;

import tr.com.mek.bookshelf.domain.model.person.Person;

public class TestPerson {

    private static final String testPersonName = "Test Person Name";
    private static final String testCity = "Test City";
    private static final String testMobilePhone = "Test Mobile Phone";

    public static Person getTestPerson() {
        Person person = new Person(testPersonName);
        person.setCity(testCity);
        person.setMobilePhone(testMobilePhone);
        return person;
    }
}
