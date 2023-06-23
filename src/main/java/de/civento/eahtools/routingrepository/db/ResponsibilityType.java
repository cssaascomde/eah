package de.civento.eahtools.routingrepository.db;

import lombok.Getter;

public enum ResponsibilityType {

    municipal("Stadt oder Gemeinde"),
    county("Landkreis"),
    chamber("Kammer"),
    regional_council("Regierungspräsidium"),
    central("Zentrale Zuständigkeit"),
    individual("Individuelle Zuordnung der Zuständigkeit"),
    unknown("Unbekannt");

    @Getter private final String displayType;
    ResponsibilityType(String displayType) {
        this.displayType = displayType;
    }
}
