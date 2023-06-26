package com.profitsoft.hw.data;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Getter
@Setter
@FieldNameConstants
@Document(indexName = "messages")
public class MessageData {

    @Id
    private String id;

    @Field(type = FieldType.Long)
    private long mailingId;

    @Field(type = FieldType.Text)
    private String subject;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Keyword)
    private List<String> recipients;

    @Field(type = FieldType.Keyword)
    private MessageStatus messageStatus;

    @Field(type = FieldType.Text)
    private String errorMessage;

}
