package com.profitsoft.hw.services.implementations;

import com.profitsoft.hw.data.MessageData;
import com.profitsoft.hw.data.MessageStatus;
import com.profitsoft.hw.dto.MessageDetailDto;
import com.profitsoft.hw.exceptions.MessageAlreadyExistException;
import com.profitsoft.hw.messaging.ReceivedMessage;
import com.profitsoft.hw.repository.implementations.MessageRepository;
import com.profitsoft.hw.services.interfaces.MessageServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService implements MessageServiceInterface {

    private final MessageRepository messageRepository;

    @Override
    public String saveMessage(ReceivedMessage receivedMessage) {
        if (!messageRepository.exist(receivedMessage.getMailingId())) {
            MessageData messageToSave = receivedMessageToData(receivedMessage);
            return messageRepository.saveMessage(messageToSave);
        }
        throw new MessageAlreadyExistException("Message with mailing id %s already received".formatted(receivedMessage.getMailingId()));
    }

    @Override
    public List<MessageDetailDto> getNotSucceedMessages() {
        return messageRepository.getMessagesWithErrorStatus().stream()
                .map(this::messageDataToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateMessageStatus(MessageDetailDto message) {
        MessageData messageData = messageRepository.findById(message.getId()).orElseThrow();
        updateMessageStatus(messageData, message);
        messageRepository.saveMessage(messageData);
    }

    private void updateMessageStatus(MessageData messageData, MessageDetailDto message) {
        messageData.setMessageStatus(message.getMessageStatus());
        messageData.setErrorMessage(message.getErrorMessage());
    }

    private MessageData receivedMessageToData(ReceivedMessage receivedMessage) {
        MessageData messageData = new MessageData();

        messageData.setSubject(receivedMessage.getSubject());
        messageData.setContent(receivedMessage.getContent());
        messageData.setRecipients(receivedMessage.getRecipients());
        messageData.setMessageStatus(MessageStatus.PENDING);

        return messageData;
    }

    private MessageDetailDto messageDataToDto(MessageData messageData) {
        return MessageDetailDto.builder()
                .id(messageData.getId())
                .subject(messageData.getSubject())
                .content(messageData.getContent())
                .recipients(messageData.getRecipients())
                .messageStatus(messageData.getMessageStatus())
                .errorMessage(messageData.getErrorMessage())
                .build();
    }

}
