package com.profitsoft.hw.services;

import com.profitsoft.hw.data.MessageStatus;
import com.profitsoft.hw.dto.MessageDetailDto;
import com.profitsoft.hw.services.implementations.MessageService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class MailService {

    private final JavaMailSender mailSender;

    private final MessageService messageService;

    @Value("${sender-email}")
    private String senderEmail;

    @Scheduled(fixedDelay = 500000L, initialDelay = 200000L)
    public void resendNotSucceedMessages() {
        List<MessageDetailDto> notSucceedMessages = messageService.getNotSucceedMessages();
        notSucceedMessages.forEach(this::sendMessage);
    }

    public void sendMessage(MessageDetailDto message) {
        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper mailSettings = new MimeMessageHelper(mail, "utf-8");

        try {
            setSettings(mailSettings, message);
            mailSender.send(mail);
            updateMessageStatus(message, MessageStatus.SUCCESS, null);
        } catch (Exception e) {
            log.error(e);
            updateMessageStatus(
                    message,
                    MessageStatus.ERROR,
                    String.format("%s: %s", e.getClass(), e.getMessage())
            );
        }
    }

    private void setSettings(MimeMessageHelper mailSettings, MessageDetailDto message) throws MessagingException {
        String[] recipients = message.getRecipients().toArray(new String[0]);

        mailSettings.setFrom(senderEmail);
        mailSettings.setSubject(message.getSubject());
        mailSettings.setText(message.getContent());
        mailSettings.setTo(recipients);

    }

    private void updateMessageStatus(MessageDetailDto message, MessageStatus newStatus, String errorMessage) {
        message.setMessageStatus(newStatus);
        message.setErrorMessage(errorMessage);

        messageService.updateMessageStatus(message);
    }

}
