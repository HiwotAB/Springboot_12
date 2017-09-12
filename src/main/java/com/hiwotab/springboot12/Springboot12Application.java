package com.hiwotab.springboot12;

import com.google.common.collect.Lists;
import it.ozimov.springboot.mail.configuration.EnableEmailTools;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;
import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableEmailTools
public class Springboot12Application {

	public static void main(final String ...args) {
		SpringApplication.run(Springboot12Application.class, args);
	}
	@Autowired
	public EmailService emailService;

	public void sendEmailWithTemplating(String recipient) throws AddressException, CannotSendEmailException {
		final Email email = DefaultEmail.builder()
				.from(new InternetAddress("http://www.cic@mala-tem.com"))
				.to(Lists.newArrayList(new InternetAddress("person2@m.com")))
				.subject("a Subject")
				.body("stuff in the body of the email")
				.encoding("UTF-8")
				.build();
		final Map<String,Object> modelObject=new HashMap<>();
		modelObject.put("recipient",recipient);
		emailService.send(email,"emailtemp",modelObject);
	}

}
