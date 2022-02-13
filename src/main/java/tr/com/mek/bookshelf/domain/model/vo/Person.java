package tr.com.mek.bookshelf.domain.model.vo;

import lombok.Value;

@Value(staticConstructor = "of")
public class Person {

    String name;
    String mobilePhone;
    String city;
}
