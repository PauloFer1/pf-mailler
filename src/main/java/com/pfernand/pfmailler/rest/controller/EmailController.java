package com.pfernand.pfmailler.rest.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfernand.pfmailler.model.Email;
import com.pfernand.pfmailler.rest.views.MaillerResponse;
import com.pfernand.pfmailler.service.MaillerServiceSpringImpl;
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
    private MaillerServiceSpringImpl maillerServiceSpring;

    @Inject
    public EmailController(
            Clock clock,
            ObjectMapper objectMapper,
            MaillerServiceSpringImpl maillerServiceSpring
    ) {
        this.clock = clock;
        this.objectMapper = objectMapper;
        this.maillerServiceSpring = maillerServiceSpring;
    }

    @ApiOperation("Sends an email without attachement")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "AUTH-TOKEN",
                    value = "AUTH-TOKEN",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Accepted"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "MaillerException"),
    })
    @RequestMapping(value = "/send", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity sendEmail(@RequestBody final Email emailParameters) throws Exception {
        log.debug("PUT /send with params: {}", emailParameters.toString());
        maillerServiceSpring.sendSimpleMessageAndSave(emailParameters);
        return ResponseEntity.ok(
                MaillerResponse.builder()
                .currentTime(Instant.now(clock).toEpochMilli())
                .inputParameters(objectMapper.valueToTree(emailParameters))
                .build()
        );
    }
}
