package com.profitsoft.hw.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateMessageDto {

    private long mailingId;
    private String subject;
    private String content;
    private List<String> recipients;

}
