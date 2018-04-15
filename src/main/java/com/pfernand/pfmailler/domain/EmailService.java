package com.pfernand.pfmailler.domain;

import com.pfernand.pfmailler.model.Email;
import com.pfernand.pfmailler.rest.exceptions.MaillerException;
import java.util.List;

public interface EmailService {

    void saveEmail(Email email) throws MaillerException;

    List<Email> getEmails();
}
