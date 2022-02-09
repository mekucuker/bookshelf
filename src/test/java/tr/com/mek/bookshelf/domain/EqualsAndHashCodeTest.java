package tr.com.mek.bookshelf.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Equals and Hash Code Test Cases")
public class EqualsAndHashCodeTest {

    private final String modelPackage = "tr.com.mek.bookshelf.domain.model";

    @Test
    @DisplayName("Testing Equals and Hash Code Methods of All Model Classes")
    void testEqualsAndHashCodeForAllDomainClasses() {
        EqualsVerifier.simple()
                .forPackage(modelPackage, true)
                .verify();
    }
}