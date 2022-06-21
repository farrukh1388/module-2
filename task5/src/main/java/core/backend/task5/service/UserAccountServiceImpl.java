package core.backend.task5.service;

import core.backend.task5.dao.ExchangeArguments;
import core.backend.task5.dao.UserAccountDao;
import core.backend.task5.exception.ValidationException;
import core.backend.task5.model.Currency;
import core.backend.task5.model.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Optional;

public class UserAccountServiceImpl implements UserAccountService {

    private static final Logger logger = LoggerFactory.getLogger(UserAccountServiceImpl.class);

    private final UserAccountDao userAccountDao;

    public UserAccountServiceImpl(UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
    }

    @Override
    public Optional<UserAccount> readUserAccount(long id) {
        return userAccountDao.readUserAccount(id);
    }

    @Override
    public void saveUserAccount(UserAccount userAccount) {
        userAccountDao.saveUserAccount(userAccount);
    }

    @Override
    public void exchange(long userId, Currency from, Currency to, BigDecimal amount) {
        Optional<UserAccount> userAccountOptional = readUserAccount(userId);
        if (userAccountOptional.isPresent()) {
            try {
                UserAccount userAccount = userAccountOptional.get();
                if (userAccount.exchange(from, to, amount)) {
                    String message = String.format("Successfully exchanged for user account with id %d, %s%s to %s",
                            userId, amount.toString(), from, to);
                    logger.info(message);
                    logger.info("User account: " + userAccount);
                    saveUserAccount(userAccount);
                    return;
                }
                logger.warn("Can't exchange because account balance is low, id: {}, from {}, to {}, amount {}", userId, from, to, amount);
                throw new ValidationException("Can't exchange because account balance is low");
            } finally {
                userAccountDao.releaseLock(userAccountOptional.get().getId());
            }
        }
        logger.warn("Can't exchange because user account not found");
        throw new ValidationException("Can't exchange because user account not found");
    }

    @Override
    public void exchange(ExchangeArguments exchangeArguments) {
        exchange(exchangeArguments.getId(), exchangeArguments.getFrom(), exchangeArguments.getTo(), exchangeArguments.getAmount());
    }
}
