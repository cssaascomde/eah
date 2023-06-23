package de.civento.eahtools.routingrepository.impl.service;

import de.civento.eahtools.routingrepository.base.businessobjects.SearchObject;
import de.civento.eahtools.routingrepository.db.DeliveryType;
import de.civento.eahtools.routingrepository.db.ResponsibilityType;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceSearchObject extends SearchObject {
    private String leikaKey;
    private String civentoKey;
    private String name;
    private ResponsibilityType responsibilityType;
    private DeliveryType deliveryType;
}
