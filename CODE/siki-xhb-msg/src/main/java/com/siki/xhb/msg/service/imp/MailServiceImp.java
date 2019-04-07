package com.siki.xhb.msg.service.imp;

import com.siki.xhb.msg.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
public class MailServiceImp implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.from}")
    private String from;

    @Override
    public boolean sendTemplateMail(String to, String subject, String validCode) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(validCode, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            log.info("------------->邮件发送失败");
            return false;
        }
        return true;
    }

}
