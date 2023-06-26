package com.profitsoft.hw.listener;

import com.profitsoft.hw.data.MessageStatus;
import com.profitsoft.hw.dto.MessageDetailDto;
import com.profitsoft.hw.messaging.ReceivedMessage;
import com.profitsoft.hw.services.MailService;
import com.profitsoft.hw.services.implementations.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageListener {

    private final MessageService messageService;

    private final MailService mailService;

    @KafkaListener(topics = "${kafka.topic.service}")
    public void messageReceived(ReceivedMessage receivedMessage) {
        String id = messageService.saveMessage(receivedMessage);
        mailService.sendMessage(getMessageDto(receivedMessage, id));
    }

    private MessageDetailDto getMessageDto(ReceivedMessage receivedMessage, String id) {
        return MessageDetailDto.builder()
                .id(id)
                .mailingId(receivedMessage.getMailingId())
                .subject(receivedMessage.getSubject())
                .content(receivedMessage.getContent())
                .recipients(receivedMessage.getRecipients())
                .messageStatus(MessageStatus.PENDING)
                .build();
    }
}
