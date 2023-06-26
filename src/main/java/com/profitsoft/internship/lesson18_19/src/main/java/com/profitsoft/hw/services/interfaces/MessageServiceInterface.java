package com.profitsoft.hw.services.interfaces;

import com.profitsoft.hw.dto.MessageDetailDto;
import com.profitsoft.hw.messaging.ReceivedMessage;

import java.util.List;

public interface MessageServiceInterface {

    String saveMessage(ReceivedMessage receivedMessage);

    List<MessageDetailDto> getNotSucceedMessages();

    void updateMessageStatus(MessageDetailDto message);

}
