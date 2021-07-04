package com.ebanx.api.dto;

import com.ebanx.api.entity.IAccount;
import com.ebanx.api.entity.Origin;

public class OriginDTO implements IAccount {
    public Origin origin;

    public OriginDTO() {
    }

    public OriginDTO(Origin origin) {
        this.origin = origin;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }
}
