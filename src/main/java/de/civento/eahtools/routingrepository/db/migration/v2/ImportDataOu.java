package de.civento.eahtools.routingrepository.db.migration.v2;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImportDataOu {
    private String uuid;
    private String id;
    private String name;
    private String address;
    private String zipcode;
    private String city;
    private String email;
    private String phone;
    private String regionalKey;
}
