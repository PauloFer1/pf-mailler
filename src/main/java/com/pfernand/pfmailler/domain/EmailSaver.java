package com.pfernand.pfmailler.domain;

import com.pfernand.pfmailler.model.Email;
import com.pfernand.pfmailler.repository.EmailDAO;
import com.pfernand.pfmailler.rest.exceptions.MaillerException;
import javax.inject.Named;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;

@Slf4j
@Named
public class EmailSaver {

    private final Jdbi jdbi;

    public EmailSaver(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public void saveEmail(Email email) throws MaillerException {
        try {
            jdbi.useExtension(EmailDAO.class, dao -> dao.insertEmail(email));
        } catch (Exception e) {
            log.error("Email failed to save with params to[{}], from[{}], subject[{}], body[{}]",
                    email.getTo(), email.getFrom(), email.getSubject(),  email.getBody());
            throw new MaillerException(
                    String.format("Email failed to save with params to[{}], from[{}], subject[{}], body[{}]",
                            email.getTo(), email.getFrom(), email.getSubject(),  email.getBody()));
        }
        log.info("Email from: {} saved", email.getFrom());
    }
}
