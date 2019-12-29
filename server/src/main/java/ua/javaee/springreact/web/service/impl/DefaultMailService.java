package ua.javaee.springreact.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ua.javaee.springreact.web.entity.Room;
import ua.javaee.springreact.web.entity.User;
import ua.javaee.springreact.web.entity.UserDevice;

import javax.transaction.Transactional;

import static org.apache.commons.lang.StringUtils.isBlank;

@Service
public class DefaultMailService {

    @Autowired
    public JavaMailSender emailSender;

    private Logger logger = LoggerFactory.getLogger(DefaultMailService.class);

    @Transactional
    public void sendFixMessage(UserDevice userDevice, String value) {
        User user = userDevice.getUser();
        StringBuilder sb = new StringBuilder();
        sb.append("Dear");
        sb.append("\t");
        sb.append(user.getLogin());
        sb.append("!");
        sb.append("\n");
        sb.append("");
        sb.append(userDevice.getValueType().toLowerCase());
        sb.append(" was fixed in a room::");
        sb.append(userDevice.getRoom().getRoomName());
        sendSimpleMessage(user.getEmail(),"fix",sb.toString());
    }

    @Transactional
    public void sendAlertMessage(UserDevice userDevice, String value) {
        User user = userDevice.getUser();
        Room room = userDevice.getRoom();
        StringBuilder sb = new StringBuilder();
        sb.append("Dear");
        sb.append("\t");
        sb.append(user.getLogin());
        sb.append("!");
        sb.append("\n");
        sb.append("You have a suspicious ");
        sb.append(userDevice.getValueType().toLowerCase());
        sb.append(" value");
        sb.append("(");
        sb.append(value);
        sb.append(")");
        sb.append("Please check:");
        sb.append(room.getRoomName());
        sendSimpleMessage(user.getEmail(),"alert",sb.toString());
    }

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
