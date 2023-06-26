package com.profitsoft.hw.repository.implementations;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import com.profitsoft.hw.data.MessageData;
import com.profitsoft.hw.data.MessageStatus;
import com.profitsoft.hw.repository.interfaces.MessageRepositoryInterface;
import lombok.AllArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class MessageRepository implements MessageRepositoryInterface {

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public Optional<MessageData> findById(String id) {
        return Optional.ofNullable(elasticsearchOperations.get(id, MessageData.class));
    }

    @Override
    public String saveMessage(MessageData messageData) {
        return elasticsearchOperations.save(messageData).getId();
    }

    @Override
    public List<MessageData> getMessagesWithErrorStatus() {
        Criteria criteria = new Criteria(MessageData.Fields.messageStatus)
                .is(MessageStatus.ERROR)
                .or(MessageData.Fields.messageStatus)
                .is(MessageStatus.PENDING);
        Query searchQuery = new CriteriaQuery(criteria);

        SearchHits<MessageData> searchHits = elasticsearchOperations.search(searchQuery, MessageData.class);

        return searchHits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    @Override
    public boolean exist(long mailingId) {
        Criteria criteria = new Criteria(MessageData.Fields.mailingId)
                .is(mailingId);
        Query searchQuery = new CriteriaQuery(criteria);

        SearchHits<MessageData> searchHits = elasticsearchOperations.search(searchQuery, MessageData.class);

        return !searchHits.isEmpty();
    }
}
