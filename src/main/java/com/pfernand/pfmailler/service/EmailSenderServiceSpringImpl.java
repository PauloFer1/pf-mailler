package com.pfernand.pfmailler.service;

import com.pfernand.pfmailler.domain.EmailServiceJdbi;
import com.pfernand.pfmailler.domain.validation.EmailValidator;
import com.pfernand.pfmailler.model.Email;
import java.time.LocalDateTime;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Slf4j
@Named
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class EmailSenderServiceSpringImpl implements EmailSenderService {

    private final JavaMailSender javaMailSender;
    private final EmailServiceJdbi emailSaver;
    @Qualifier("emailValidatorJavaMail")
    private final EmailValidator emailValidator;


    @Override
    public void sendSimpleMessage(Email email) throws Exception {
        log.info("Sending email to: {}", email.getTo());
        try {
            emailValidator.isValidEmail(email);
            email.setValid(true);
            // TODO: this should be asynchronous
            javaMailSender.send(map(email));
            email.setSentTime(LocalDateTime.now());
        } finally {
            email.setCreatedAt(LocalDateTime.now());
            emailSaver.saveEmail(email);
        }
    }

    // TODO: Refactor to its own class
    private SimpleMailMessage map(Email email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email.getTo());
        simpleMailMessage.setSubject(email.getSubject());
        simpleMailMessage.setText(email.getBody());
        simpleMailMessage.setFrom(email.getFrom());
        simpleMailMessage.setReplyTo(email.getFrom());
        return simpleMailMessage;
    }

}
