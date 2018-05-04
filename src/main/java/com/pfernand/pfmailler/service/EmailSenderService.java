package com.pfernand.pfmailler.service;

import com.pfernand.pfmailler.model.Email;
import com.pfernand.pfmailler.rest.exceptions.MaillerException;

public interface EmailSenderService {

    void sendSimpleMessage(Email email) throws MaillerException;
}
