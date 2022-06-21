package core.backend.task5.model;

import java.math.BigDecimal;

public class Account {

    private Currency currency;
    private BigDecimal amount;

    public Account(Currency currency, BigDecimal amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public Account() {
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "currency=" + currency +
                ", amount=" + amount +
                '}';
    }
}
