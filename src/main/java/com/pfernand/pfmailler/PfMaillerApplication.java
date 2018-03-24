package com.pfernand.pfmailler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableAutoConfiguration
@Configuration
@ComponentScan(value = "com.pfernand.pfmailler")
public class PfMaillerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PfMaillerApplication.class, args);
	}
}
