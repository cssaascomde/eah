package de.civento.eahtools.routingrepository.db;

import lombok.Getter;

public enum DeliveryType {
    internal("intern"),
    xta("XTA"),
    email("Email");


    @Getter
    private final String displayType;
    DeliveryType(String displayType) {
        this.displayType = displayType;
    }
}
