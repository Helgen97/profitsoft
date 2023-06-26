package com.profitsoft.hw.dto;

import com.profitsoft.hw.data.MessageStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MessageDetailDto {

    private String id;
    private long mailingId;
    private String subject;
    private String content;
    private List<String> recipients;
    private MessageStatus messageStatus;
    private String errorMessage;
}
