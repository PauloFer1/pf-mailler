package com.pfernand.pfmailler.model.validator;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ValidateEmailField {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime validateAndGet(String date) {
        if ( date == null) {
            return null;
        }
        return LocalDateTime.parse(date, FORMATTER);
    }
}
