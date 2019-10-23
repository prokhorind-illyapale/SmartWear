package ua.javaee.springreact.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang.StringUtils.isBlank;

@Service
public class DefaultMailService {

    @Autowired
    public JavaMailSender emailSender;

    private Logger logger = LoggerFactory.getLogger(DefaultMailService.class);

    public void sendSimpleMessage(String to, String subject, String text) {
        if (isBlank(to)) {
            return;
        }
        SimpleMailMessage message = createMessage(to, subject, text);
        try {
            emailSender.send(message);
        } catch (MailException e) {
            logger.error("Mail was not send to:" + to);
            logger.error(e.getMessage());
        }
    }

    private SimpleMailMessage createMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        return message;
    }
}
