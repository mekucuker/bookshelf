package tr.com.mek.bookshelf.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Equals and Hash Code Test Cases")
public class EqualsAndHashCodeTest {

    private final String domainPackage = "tr.com.mek.bookshelf.model";

    @Test
    @DisplayName("Testing Equals and Hash Code Methods of All Model Classes")
    public void testEqualsAndHashCodeForAllDomainClasses() {
        EqualsVerifier.simple()
                .forPackage(domainPackage, true)
                .verify();
    }
}