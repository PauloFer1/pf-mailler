package com.pfernand.pfmailler.service;

import com.pfernand.pfmailler.domain.EmailServiceJdbi;
import com.pfernand.pfmailler.domain.EmailValidator;
import com.pfernand.pfmailler.model.Email;
import com.pfernand.pfmailler.rest.exceptions.MaillerException;
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
    @Qualifier("emailValidatorMx")
    private final EmailValidator emailValidatorMx;


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
            throw new MaillerException("Email failed to send", e);
        }
    }

    public void sendSimpleMessageAndSave(Email email) throws MaillerException {

        try {
            emailValidatorMx.isValidEmail(email);
            sendSimpleMessage(
                    email.getTo(),
                    email.getFrom(),
                    email.getSubject(),
                    email.getBody()
            );
        } finally {
            email.setSentTime(LocalDateTime.now());
            emailSaver.saveEmail(email);
        }
    }

}
