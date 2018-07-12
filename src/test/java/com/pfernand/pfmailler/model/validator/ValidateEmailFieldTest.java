package com.pfernand.pfmailler.model.validator;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ValidateEmailFieldTest {


    @Test
    public void validateAndGetReturnsNullIfParamisNull() {
        // Given
        String date =  null;

        // When
        LocalDateTime localDateTime = ValidateEmailField.validateAndGet(date);

        // Then
        assertNull(localDateTime);
    }

    @Test
    public void validateAndGetReturnsCorrectLocalDateTime() {
        // Given
        String date = "2018-07-08 21:09:10";

        // When
        LocalDateTime localDateTime = ValidateEmailField.validateAndGet(date);

        // Then
        assertEquals(LocalDateTime.of(2018, 7, 8, 21, 9,10), localDateTime);
    }

}