package de.civento.eahtools.routingrepository.db.migration.v2;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImportDataService {
    private String uuid;
    private String id;
    private String name;
}
