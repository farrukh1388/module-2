package core.backend.task5.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.backend.task5.exception.ValidationException;
import core.backend.task5.model.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserAccountDaoImpl implements UserAccountDao {

    private static final Logger logger = LoggerFactory.getLogger(UserAccountDaoImpl.class);

    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<Long, Boolean> locksMap = new HashMap<>();

    @Override
    public Optional<UserAccount> readUserAccount(long id) {
        try {
            lock(id);
            Optional<UserAccount> userAccountOptional = Optional.ofNullable(
                    mapper.readValue(Files.readAllBytes(Paths.get(getBasePath() + "/" + id)), UserAccount.class));
            if (userAccountOptional.isEmpty()) {
                releaseLock(id);
            }
            return userAccountOptional;
        } catch (IOException e) {
            logger.error("Can't read user account with id: " + id, e);
            return Optional.empty();
        }
    }

    @Override
    public boolean saveUserAccount(UserAccount userAccount) {
        if (userAccount == null) {
            logger.error("User account can't be null");
            throw new ValidationException("User account can't be null");
        }
        if (!Files.exists(getBasePath())) {
            createPath();
        }
        long id = userAccount.getId();
        try {
            String path = getBasePath() + "/" + id;
            File file = new File(path);
            file.delete();
            file.createNewFile();
            Path path1 = Paths.get(path);
            Files.write(path1, mapper.writeValueAsBytes(userAccount));
            locksMap.put(id, false);
            return true;
        } catch (IOException e) {
            logger.error("Can't write user account to file", e);
            return false;
        }
    }

    public void releaseLock(long id) {
        synchronized (locksMap) {
            locksMap.put(id, false);
        }
    }

    public void lock(long id) {
        while (isLocked(id)) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }

        synchronized (locksMap) {
            locksMap.put(id, true);
        }
    }

    public boolean isLocked(long id) {
        synchronized (locksMap) {
            if (locksMap.containsKey(id)) {
                return locksMap.get(id);
            }
        }
        logger.error("Can't find user account in locks map with id " + id);
        throw new ValidationException("Can't find user account in locks map with id " + id);
    }

    private void createPath() {
        try {
            Files.createDirectories(getBasePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path getBasePath() {
        return Paths.get("files/");
    }
}
