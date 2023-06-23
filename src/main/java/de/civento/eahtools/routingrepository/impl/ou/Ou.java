package de.civento.eahtools.routingrepository.impl.ou;

import de.civento.eahtools.routingrepository.base.businessobjects.BusinessObject;
import de.civento.eahtools.routingrepository.db.OuType;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ou extends BusinessObject {
    private String civentoKey;
    private String regionalKey;
    private String name;
    private String address;
    private String zipCode;
    private String city;
    private OuType type;
}
