package com.pfernand.pfmailler.domain;

import com.pfernand.pfmailler.model.Email;
import com.pfernand.pfmailler.repository.EmailDAO;
import com.pfernand.pfmailler.rest.exceptions.MaillerException;
import java.util.List;
import javax.inject.Named;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;

@Slf4j
@Named
public class EmailServiceJdbi implements EmailService {

    private final Jdbi jdbi;

    public EmailServiceJdbi(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public void saveEmail(Email email) throws MaillerException {
        try {
            jdbi.useExtension(EmailDAO.class, dao -> dao.insertEmail(email));
        } catch (Exception e) {
            log.error("Email failed to save with params to[{}], from[{}], subject[{}], body[{}]",
                    email.getTo(), email.getFrom(), email.getSubject(),  email.getBody());
            throw new MaillerException(
                    String.format("Email failed to save with params to[%s], from[%s], subject[%s], body[%s]",
                            email.getTo(), email.getFrom(), email.getSubject(),  email.getBody()));
        }
        log.info("Email from: {} saved", email.getFrom());
    }

    @Override
    public List<Email> getEmails() {
        return jdbi.withExtension( EmailDAO.class, dao -> {return dao.selectAllEmail();});
    }

}
