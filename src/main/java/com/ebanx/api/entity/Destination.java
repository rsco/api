package com.ebanx.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;

public class Destination {

    public String id;
    public BigDecimal balance;

    public Destination() {
    }

    public Destination(String id, BigDecimal balance) {
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
