package com.pedrolima.springrest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.pedrolima.springrest.services.DBService;
import com.pedrolima.springrest.services.EmailService;
import com.pedrolima.springrest.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String stategy;
	
	@Bean
	public boolean instantiateDatabase() {
		if(!"create".equals(stategy)) {
			return false;
		}
		dbService.instantiateDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailService()	{
		return new SmtpEmailService();
	}
}
