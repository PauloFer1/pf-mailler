package com.pfernand.pfmailler.domain;

import com.pfernand.pfmailler.model.Email;
import com.pfernand.pfmailler.rest.exceptions.MaillerException;
import java.util.List;

public interface EmailService {

    /**
     * Audit Email in the DB
     * @param email - Email object
     * @throws MaillerException
     */
    void saveEmail(Email email) throws MaillerException;

    /**
     * Get all audit emails
     * @return
     */
    List<Email> getEmails();

}
