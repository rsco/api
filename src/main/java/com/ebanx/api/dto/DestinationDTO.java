package com.ebanx.api.dto;

import com.ebanx.api.entity.Destination;
import com.ebanx.api.entity.IAccount;

public class DestinationDTO implements IAccount {
    public Destination destination;

    public DestinationDTO() {
    }

    public DestinationDTO(Destination destination) {
        this.destination = destination;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }
}
