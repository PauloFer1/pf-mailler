package com.pfernand.pfmailler.service;

import com.pfernand.pfmailler.rest.exceptions.MaillerException;

public interface MaillerService {

    void sendSimpleMessage(String to, String from, String subject, String body) throws MaillerException;
}