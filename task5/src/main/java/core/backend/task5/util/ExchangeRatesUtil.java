package core.backend.task5.util;

import core.backend.task5.dao.ExchangeArguments;
import core.backend.task5.model.Account;
import core.backend.task5.model.Currency;
import core.backend.task5.model.UserAccount;
import core.backend.task5.service.UserAccountService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public final class ExchangeRatesUtil {

    private static final Map<Map<Currency, Currency>, BigDecimal> exchangeRates = initializeExchangeRates();
    private static final Random random = new Random();

    public static BigDecimal getExchangeRate(Currency from, Currency to) {
        return exchangeRates.get(Collections.singletonMap(from, to));
    }

    public static void createSampleAccounts(UserAccountService userAccountService) {
        Account usdAccount = new Account(Currency.USD, BigDecimal.valueOf(1000));
        Account eurAccount = new Account(Currency.EUR, BigDecimal.valueOf(1000));
        Account gbpAccount = new Account(Currency.GBP, BigDecimal.valueOf(1000));
        Account jpyAccount = new Account(Currency.JPY, BigDecimal.valueOf(1000));
        Map<Currency, Account> accounts = new HashMap<>();
        accounts.put(Currency.USD, usdAccount);
        accounts.put(Currency.EUR, eurAccount);
        accounts.put(Currency.GBP, gbpAccount);
        accounts.put(Currency.JPY, jpyAccount);

        UserAccount firstUserAccount = new UserAccount(1L, "first", accounts);
        UserAccount secondUserAccount = new UserAccount(2L, "second", accounts);

        userAccountService.saveUserAccount(firstUserAccount);
        userAccountService.saveUserAccount(secondUserAccount);
    }

    public static List<ExchangeArguments> generateExchangeRates() {
        List<ExchangeArguments> list = new ArrayList<>();

        addArguments(list, 1);
        addArguments(list, 2);

        return list;
    }

    private static void addArguments(List<ExchangeArguments> list, long id) {
        for (int i = 0; i < 3; i++) {
            list.add(new ExchangeArguments(id, Currency.USD, Currency.EUR, random()));
            list.add(new ExchangeArguments(id, Currency.USD, Currency.GBP, random()));
            list.add(new ExchangeArguments(id, Currency.USD, Currency.JPY, random()));
            list.add(new ExchangeArguments(id, Currency.EUR, Currency.USD, random()));
            list.add(new ExchangeArguments(id, Currency.EUR, Currency.GBP, random()));
            list.add(new ExchangeArguments(id, Currency.EUR, Currency.JPY, random()));
            list.add(new ExchangeArguments(id, Currency.GBP, Currency.EUR, random()));
            list.add(new ExchangeArguments(id, Currency.GBP, Currency.USD, random()));
            list.add(new ExchangeArguments(id, Currency.GBP, Currency.JPY, random()));
            list.add(new ExchangeArguments(id, Currency.JPY, Currency.EUR, random()));
            list.add(new ExchangeArguments(id, Currency.JPY, Currency.GBP, random()));
            list.add(new ExchangeArguments(id, Currency.JPY, Currency.USD, random()));
        }
    }

    private static BigDecimal random() {
        return BigDecimal.valueOf(random.nextInt(999) + 1);
    }

    private static Map<Map<Currency, Currency>, BigDecimal> initializeExchangeRates() {
        Map<Map<Currency, Currency>, BigDecimal> exchangeRates = new HashMap<>();
        exchangeRates.put(Collections.singletonMap(Currency.USD, Currency.EUR), BigDecimal.valueOf(0.95));
        exchangeRates.put(Collections.singletonMap(Currency.USD, Currency.GBP), BigDecimal.valueOf(0.82));
        exchangeRates.put(Collections.singletonMap(Currency.USD, Currency.JPY), BigDecimal.valueOf(135.11));
        exchangeRates.put(Collections.singletonMap(Currency.EUR, Currency.USD), BigDecimal.valueOf(1.05));
        exchangeRates.put(Collections.singletonMap(Currency.EUR, Currency.GBP), BigDecimal.valueOf(0.86));
        exchangeRates.put(Collections.singletonMap(Currency.EUR, Currency.JPY), BigDecimal.valueOf(141.89));
        exchangeRates.put(Collections.singletonMap(Currency.GBP, Currency.USD), BigDecimal.valueOf(1.22));
        exchangeRates.put(Collections.singletonMap(Currency.GBP, Currency.EUR), BigDecimal.valueOf(1.17));
        exchangeRates.put(Collections.singletonMap(Currency.GBP, Currency.JPY), BigDecimal.valueOf(165.47));
        exchangeRates.put(Collections.singletonMap(Currency.JPY, Currency.USD), BigDecimal.valueOf(0.0074));
        exchangeRates.put(Collections.singletonMap(Currency.JPY, Currency.EUR), BigDecimal.valueOf(0.007));
        exchangeRates.put(Collections.singletonMap(Currency.JPY, Currency.GBP), BigDecimal.valueOf(0.006));
        return exchangeRates;
    }

    private ExchangeRatesUtil() {
    }
}
