package com.profitsoft.hw.controller;

import com.profitsoft.hw.dto.CreateMessageDto;
import com.profitsoft.hw.messaging.ReceivedMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OtherServiceController {

    @Value("${kafka.topic.service}")
    private String messageService;

    private final KafkaOperations<String, ReceivedMessage> kafkaOperations;

    @PostMapping("/send")
    public void receiveMessageToSend(@RequestBody CreateMessageDto newMessage) {
        kafkaOperations.send(messageService, newMessage.getSubject(), toMessage(newMessage));
    }

    private ReceivedMessage toMessage(CreateMessageDto messageDto) {
        return ReceivedMessage.builder()
                .mailingId(messageDto.getMailingId())
                .subject(messageDto.getSubject())
                .content(messageDto.getContent())
                .recipients(messageDto.getRecipients())
                .build();
    }
}
