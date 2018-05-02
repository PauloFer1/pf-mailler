package com.pfernand.pfmailler.service;

import com.pfernand.pfmailler.domain.EmailServiceJdbi;
import com.pfernand.pfmailler.domain.validation.EmailValidator;
import com.pfernand.pfmailler.model.Email;
import com.pfernand.pfmailler.rest.exceptions.MaillerException;
import java.time.LocalDateTime;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Slf4j
@Named
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class EmailSenderServiceSpringImpl implements EmailSenderService {

    private final JavaMailSender javaMailSender;
    private final EmailServiceJdbi emailSaver;
    @Qualifier("emailValidatorMx")
    private final EmailValidator emailValidatorMx;


    @Override
    public void sendSimpleMessage(Email email) throws MaillerException {
        log.info("Sending email to: {}", email.getTo());
        try {
            emailValidatorMx.isValidEmail(email);
            email.setValid(true);
            javaMailSender.send(buildMessage(email));
            email.setSentTime(LocalDateTime.now());
        } catch (MailException e) {
            log.error("Email failed to send with params: {}", email.toString());
            throw new MaillerException("Email failed to send", e);
        } finally {
            email.setCreatedAt(LocalDateTime.now());
            emailSaver.saveEmail(email);
        }
    }

    private SimpleMailMessage buildMessage(Email email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email.getTo());
        simpleMailMessage.setSubject(email.getSubject());
        simpleMailMessage.setText(email.getBody());
        simpleMailMessage.setFrom(email.getFrom());
        simpleMailMessage.setReplyTo(email.getFrom());
        return simpleMailMessage;
    }

}
