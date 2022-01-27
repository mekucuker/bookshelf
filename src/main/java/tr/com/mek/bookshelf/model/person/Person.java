package tr.com.mek.bookshelf.model.person;

import lombok.Data;
import tr.com.mek.bookshelf.model.person.value.City;
import tr.com.mek.bookshelf.model.person.value.PhoneNumber;

@Data
public class Person {

    private final String name;
    private PhoneNumber phoneNumber;
    private City city;
}
