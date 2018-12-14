package com.pfernand.pfmailler.service;

import com.pfernand.pfmailler.model.Email;

public interface EmailSenderService {

    void sendSimpleMessage(Email email) throws Exception;
}
