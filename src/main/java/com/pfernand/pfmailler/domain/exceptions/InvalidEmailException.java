package com.pfernand.pfmailler.domain.exceptions;

import com.pfernand.pfmailler.rest.exceptions.MaillerException;

public class InvalidEmailException extends MaillerException {
    public InvalidEmailException(String email) {
        super(String.format("Invalid email: %s", email));
    }
}
