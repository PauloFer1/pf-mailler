package com.pfernand.pfmailler.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfernand.pfmailler.domain.EmailService;
import com.pfernand.pfmailler.model.Email;
import com.pfernand.pfmailler.rest.views.EmailListResponse;
import com.pfernand.pfmailler.rest.views.MaillerResponse;
import com.pfernand.pfmailler.service.EmailSenderServiceSpringImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.time.Clock;
import java.time.Instant;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class EmailController {

    private final Clock clock;
    private final ObjectMapper objectMapper;
    private final EmailSenderServiceSpringImpl maillerServiceSpring;
    private final EmailService emailService;

    @Inject
    public EmailController(
        Clock clock,
        ObjectMapper objectMapper,
        EmailSenderServiceSpringImpl maillerServiceSpring,
        EmailService emailService
    ) {
        this.clock = clock;
        this.objectMapper = objectMapper;
        this.maillerServiceSpring = maillerServiceSpring;
        this.emailService = emailService;
    }

    @ApiOperation("Sends an email without attachement")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization  ",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Accepted"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "MaillerException"),
    })
    @RequestMapping(value = "/send", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity sendEmail(@RequestBody final Email emailParameters) throws Exception {
        log.info("POST /send with params: {}", emailParameters.toString());
        maillerServiceSpring.sendSimpleMessage(emailParameters);
        return ResponseEntity.ok(
                MaillerResponse.builder()
                .currentTime(Instant.now(clock).toEpochMilli())
                .inputParameters(objectMapper.valueToTree(emailParameters))
                .build()
        );
    }

    @ApiOperation("Get all emails")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Accepted"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "MaillerException"),
    })
    @RequestMapping(value = "/emails", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getEmails() throws Exception {
        log.info("GET /emails");
        return ResponseEntity.ok(
                EmailListResponse.builder()
                    .currentTime(Instant.now(clock).toEpochMilli())
                    .emails(emailService.getEmails())
                    .build()
        );
    }
}
