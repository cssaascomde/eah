package de.civento.eahtools.routingrepository.impl.ou;

import de.civento.eahtools.routingrepository.base.businessobjects.SearchObject;
import de.civento.eahtools.routingrepository.db.OuType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class OuSearchObject extends SearchObject {
    private String civentoKey;
    private String regionalKey;
    private String name;
    private OuType type;
}
