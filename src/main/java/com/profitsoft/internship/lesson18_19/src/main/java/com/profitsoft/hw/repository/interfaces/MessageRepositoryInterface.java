package com.profitsoft.hw.repository.interfaces;

import com.profitsoft.hw.data.MessageData;

import java.util.List;
import java.util.Optional;

public interface MessageRepositoryInterface {

    Optional<MessageData> findById(String id);

    String saveMessage(MessageData messageData);

    List<MessageData> getMessagesWithErrorStatus();

    boolean exist(long mailingId);
}
