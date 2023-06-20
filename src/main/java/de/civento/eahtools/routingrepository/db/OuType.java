package de.civento.eahtools.routingrepository.db;

import lombok.Getter;

public enum OuType {
    municipal_sub("Organisationseinheit einer Stadt oder Gemeinde"),
    municipal("Stadt oder Gemeinde"),
    county("Landkreis"),
    regional_council("Regierungspräsidium"),
    chamber("Kammer"),
    other("Sonstige zuständige Stellen");

    @Getter
    private final String displayType;
    OuType(String displayType) {
        this.displayType = displayType;
    }
}
