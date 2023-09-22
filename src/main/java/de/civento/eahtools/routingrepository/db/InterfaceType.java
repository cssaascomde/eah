package de.civento.eahtools.routingrepository.db;

import lombok.Getter;

public enum InterfaceType {
    xtaWithAnswer("XTA mit Antwort"),
    xtaWithoutAnswer("XTA ohne Antwort");


    @Getter
    private final String displayType;
    InterfaceType(String displayType) {
        this.displayType = displayType;
    }
}
