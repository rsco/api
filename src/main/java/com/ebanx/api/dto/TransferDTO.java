package com.ebanx.api.dto;

import com.ebanx.api.entity.Destination;
import com.ebanx.api.entity.IAccount;
import com.ebanx.api.entity.Origin;

public class TransferDTO implements IAccount {
    public Origin origin;
    public Destination destination;

    public TransferDTO() {
    }

    public TransferDTO(Origin origin, Destination destination) {
        this.destination = destination;
        this.origin = origin;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }
}
