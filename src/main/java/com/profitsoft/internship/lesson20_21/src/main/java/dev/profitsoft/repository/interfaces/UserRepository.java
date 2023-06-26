package dev.profitsoft.repository.interfaces;

import dev.profitsoft.data.UserData;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    String createUser(UserData newUser);

    Optional<UserData> findByEmail(String email);

    List<UserData> findUsersByListIds(List<String> usersId);

    void updateAll(List<UserData> usersToUpdate);

}
