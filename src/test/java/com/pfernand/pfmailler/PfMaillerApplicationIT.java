package com.pfernand.pfmailler;

import javax.inject.Inject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PfMaillerApplication.class, H2Config.class},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PfMaillerApplicationIT {

    @Inject
    private ApplicationContext applicationContext;

    @Test
    public void appSpringContextConfiguratedCorrectly() {
        Assert.assertNotNull(applicationContext);
    }
}