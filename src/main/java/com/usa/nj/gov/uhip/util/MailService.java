package com.usa.nj.gov.uhip.util;

import java.util.Date;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	private static final Logger logger = LoggerFactory.getLogger(MailService.class);
	@Autowired
	private JavaMailSender mailSender;

	public boolean sendAccRegEmail(String to, String subject, String body) {
		logger.debug("*** Sending Account Registration Email start ***");
		/*SimpleMailMessage mailMsg = new SimpleMailMessage();
		mailMsg.setSubject(subject);
		mailMsg.setText(body);

		mailMsg.setTo(to);
		mailMsg.setSentDate(new Date());

		mailSender.send(mailMsg);*/
       
		try {
		MimeMessage msg = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(body, true);
		helper.setSentDate(new Date());
		
		mailSender.send(msg);
		
		}catch (Exception e) {
			logger.error("*** Exception Occured in Java mail API *** "+e.getMessage());
		}
		logger.debug("*** Sending Account Registration Email end ***");

		logger.info("*** Sending Account Registration Email completed  ***");

		return true;

	}
}
