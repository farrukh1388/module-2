package core.backend.task5.model;

import core.backend.task5.util.ExchangeRatesUtil;

import java.math.BigDecimal;
import java.util.Map;

public class UserAccount {

    private long id;
    private String username;
    private Map<Currency, Account> accounts;

    public UserAccount(long id, String username, Map<Currency, Account> accounts) {
        this.id = id;
        this.username = username;
        this.accounts = accounts;
    }

    public UserAccount() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<Currency, Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Map<Currency, Account> accounts) {
        this.accounts = accounts;
    }

    public boolean exchange(Currency from, Currency to, BigDecimal amount) {
        synchronized (this) {
            if (canExchange(from, amount)) {
                Account fromAccount = accounts.get(from);
                BigDecimal fromAmount = fromAccount.getAmount();
                fromAmount = fromAmount.subtract(amount);
                fromAccount.setAmount(fromAmount);

                BigDecimal exchangeRate = ExchangeRatesUtil.getExchangeRate(from, to);
                Account toAccount = accounts.get(to);
                BigDecimal toAmount = toAccount.getAmount();
                toAmount = toAmount.add(amount.multiply(exchangeRate));
                toAccount.setAmount(toAmount);
                return true;
            }
            return false;
        }
    }

    private boolean canExchange(Currency from, BigDecimal amount) {
        Account fromAccount = accounts.get(from);
        BigDecimal fromAmount = fromAccount.getAmount();
        return fromAmount.compareTo(amount) >= 0;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", accounts=" + accounts +
                '}';
    }
}
