package com.ebanx.api.entity;

import java.math.BigDecimal;

public class Origin {
    public String id;
    public BigDecimal balance;

    public Origin() {
    }

    public Origin(String id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
