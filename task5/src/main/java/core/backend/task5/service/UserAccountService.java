package core.backend.task5.service;

import core.backend.task5.dao.ExchangeArguments;
import core.backend.task5.model.Currency;
import core.backend.task5.model.UserAccount;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserAccountService {

    Optional<UserAccount> readUserAccount(long id);

    void saveUserAccount(UserAccount userAccount);

    void exchange(long userId, Currency from, Currency to, BigDecimal amount);

    void exchange(ExchangeArguments exchangeArguments);
}
