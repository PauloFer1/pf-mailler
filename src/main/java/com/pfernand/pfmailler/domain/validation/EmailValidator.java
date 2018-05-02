package com.pfernand.pfmailler.domain.validation;

import com.pfernand.pfmailler.domain.exceptions.InvalidEmailException;
import com.pfernand.pfmailler.model.Email;

public interface EmailValidator {

    /**
     * Validates an email
     * @param email
     * @return
     * @throws InvalidEmailException
     */
    void isValidEmail(Email email) throws InvalidEmailException;
}
