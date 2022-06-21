package core.backend.task5.dao;

import core.backend.task5.model.Currency;

import java.math.BigDecimal;

public class ExchangeArguments {

    private long id;
    private Currency from;
    private Currency to;
    private BigDecimal amount;

    public ExchangeArguments(long id, Currency from, Currency to, BigDecimal amount) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Currency getFrom() {
        return from;
    }

    public void setFrom(Currency from) {
        this.from = from;
    }

    public Currency getTo() {
        return to;
    }

    public void setTo(Currency to) {
        this.to = to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
