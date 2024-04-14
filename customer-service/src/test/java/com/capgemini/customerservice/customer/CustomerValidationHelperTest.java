package com.capgemini.customerservice.customer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerValidationHelperTest {

    @Test
    void testValidEmail() {
        // Given
        String validEmail = "ahmed@example.com";

        // When
        boolean isValid = CustomerValidationHelper.isValidEmail(validEmail);

        // Then
        assertTrue(isValid);
    }

    @Test
    void testInvalidEmail() {
        // Given
        String invalidEmail = "invalid.email.com";

        // When
        boolean isValid = CustomerValidationHelper.isValidEmail(invalidEmail);

        // Then
        assertFalse(isValid);
    }
}
