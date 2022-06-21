package core.backend.task5.dao;

import core.backend.task5.model.UserAccount;

import java.util.Optional;

public interface UserAccountDao {

    Optional<UserAccount> readUserAccount(long id);

    boolean saveUserAccount(UserAccount userAccount);

    void releaseLock(long id);

    void lock(long id);

    boolean isLocked(long id);
}
