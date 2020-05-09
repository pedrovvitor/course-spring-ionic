package com.pedrolima.springrest.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.pedrolima.springrest.entities.Customer;
import com.pedrolima.springrest.entities.Order;

public abstract class AbstractEmailService implements EmailService{
	
	@Value("${default.sender}") // traz o conteúdo da propriedade que está dentro das chaves
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public void sendOrderConfirmationEmail(Order obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromOrder(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromOrder(Order obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCustomer().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Order confirmed! Id: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}
	
	protected String htmlFromTemplatePedido(Order obj) {
		Context context = new Context();
		context.setVariable("order", obj);
		return templateEngine.process("email/orderConfirmation", context);
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(Order obj) {
		try {
			MimeMessage mm = prepareMimeMessageFromOrder(obj);
			sendHtmlEmail(mm);
		} catch (MessagingException e) {
			sendOrderConfirmationEmail(obj);
		}
	}

	protected MimeMessage prepareMimeMessageFromOrder(Order obj) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(obj.getCustomer().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Order confirmed! Code: " + obj.getId());
		mmh.setText(htmlFromTemplatePedido(obj), true);
		return mimeMessage;
	}
	
	@Override
	public void sendNewPasswordEmail(Customer customer, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(customer, newPass);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareNewPasswordEmail(Customer customer, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(customer.getEmail());
		sm.setFrom(sender);
		sm.setSubject("New password request");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("New password: " + newPass);
		return sm;
	}
	
	

}
