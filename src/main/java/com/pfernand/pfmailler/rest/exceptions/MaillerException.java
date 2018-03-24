package com.pfernand.pfmailler.rest.exceptions;

public class MaillerException extends Exception {
    public MaillerException(final String message) {
        super(message);
    }

    public MaillerException(final String message, Throwable t) {
        super(message, t);
    }
}
