package com.restservice;

import java.util.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(scanBasePackages = "true")
@ComponentScan(value = "com.restservice")
@EnableAutoConfiguration
public class RestfulServiceApplication {

	private static final Logger LOG= LoggerFactory.getLogger(RestfulServiceApplication.class);
	
	public static void main(String[] args) {
		System.out.println("RestService Starting up => "+ Calendar.getInstance().getTimeInMillis());
		SpringApplication.run(RestfulServiceApplication.class, args);
	}
}
