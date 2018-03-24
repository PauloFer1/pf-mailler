package com.pfernand.pfmailler.service;

import com.pfernand.pfmailler.model.Email;
import com.pfernand.pfmailler.repository.EmailDAO;
import com.pfernand.pfmailler.rest.exceptions.MaillerException;
import java.time.LocalDateTime;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Slf4j
@Named
public class MaillerServiceSpringImpl implements MaillerService {

    private final JavaMailSender javaMailSender;
    private final Jdbi jdbi;

    @Inject
    public MaillerServiceSpringImpl(final JavaMailSender javaMailSender, final Jdbi jdbi) {
        this.javaMailSender = javaMailSender;
        this.jdbi = jdbi;
    }

    @Override
    public void sendSimpleMessage(String to, String from, String subject, String body) throws MaillerException {

        log.info("Sending email to: {}", to);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setReplyTo(from);

        try {
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            log.error("Email failed to send with params: to[{}], from[{}], subject[{}], body[{}]",
                    to, from, subject, body);
            throw new MaillerException("Email failed to send");
        }
    }

    public void sendSimpleMessageAndSave(Email email) throws MaillerException {

        sendSimpleMessage(
                email.getTo(),
                email.getFrom(),
                email.getSubject(),
                email.getBody()
        );
        email.setSentTime(LocalDateTime.now());

        try {
            jdbi.useExtension(EmailDAO.class, dao -> dao.insertEmail(email));
        } catch (Exception e) {
            log.error("Email failed to save with params to[{}], from[{}], subject[{}], body[{}]",
                    email.getTo(), email.getFrom(), email.getSubject(),  email.getBody());
        }
        log.info("Email from: {} saved", email.getFrom());
    }
}
