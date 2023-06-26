package dev.profitsoft.repository.implementation;

import dev.profitsoft.data.BookData;
import dev.profitsoft.data.UserData;
import dev.profitsoft.repository.interfaces.BookRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@RequiredArgsConstructor
public class BookRepo implements BookRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public String createBook(BookData newBook) {
        return mongoTemplate.save(newBook).get_id();
    }

    @Override
    public void updateBookUsers(BookData updatedBook) {
        Query query = new Query()
                .addCriteria(where(BookData.Fields._id).is(updatedBook.get_id()));
        Update updateDefinition = new Update()
                .set(BookData.Fields.bookOwners, updatedBook.getBookOwners());

        mongoTemplate.updateFirst(query, updateDefinition, BookData.class);
    }

    @Override
    public List<BookData> getBooksByUserId(String userId) {
        Query query = new Query()
                .addCriteria(where("book_owners._id").is(new ObjectId(userId)));
        return mongoTemplate.find(query, BookData.class);
    }

    @Override
    public Optional<BookData> getBookById(String bookId) {
        return Optional.ofNullable(mongoTemplate.findById(bookId, BookData.class));
    }
}
