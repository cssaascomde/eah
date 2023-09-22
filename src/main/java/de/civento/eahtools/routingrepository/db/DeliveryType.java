package de.civento.eahtools.routingrepository.db;

import lombok.Getter;

public enum DeliveryType {
    internal("interner SB-Client"),
    xta("XTA"),
    email("Email"),
    portal("Portalzugang");

    @Getter
    private final String displayType;
    DeliveryType(String displayType) {
        this.displayType = displayType;
    }
}
