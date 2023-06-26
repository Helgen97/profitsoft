package dev.profitsoft.repository.implementation;

import dev.profitsoft.data.UserData;
import dev.profitsoft.repository.interfaces.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;


@Repository
@RequiredArgsConstructor
public class UserRepo implements UserRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public String createUser(UserData newUser) {
        return mongoTemplate.save(newUser).get_id();
    }

    @Override
    public Optional<UserData> findByEmail(String email) {
        Query query = new Query()
                .addCriteria(where(UserData.Fields.email).is(email));

        return Optional.ofNullable(mongoTemplate.findOne(query, UserData.class));
    }

    @Override
    public List<UserData> findUsersByListIds(List<String> usersId) {
        Query query = new Query()
                .addCriteria(where(UserData.Fields._id).in(usersId));

        return mongoTemplate.find(query, UserData.class);
    }

    @Override
    public void updateAll(List<UserData> usersToUpdate) {
        BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, UserData.class);

        usersToUpdate.forEach((user -> {
            Query query = new Query(where(UserData.Fields._id).is(user.get_id()));
            Update update = new Update().set(UserData.Fields.userBooks, user.getUserBooks());

            bulkOps.updateOne(query, update);
        }));

        bulkOps.execute();
    }
}
