package de.civento.eahtools.routingrepository.impl.objects;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Ou {
    private String id;
    private String name;
    private String street;
    private String housenumber;
    private String zipCode;
    private String city;
}
