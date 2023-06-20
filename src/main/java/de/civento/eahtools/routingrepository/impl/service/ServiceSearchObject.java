package de.civento.eahtools.routingrepository.impl.service;

import de.civento.eahtools.routingrepository.base.businessobjects.SearchObject;
import de.civento.eahtools.routingrepository.db.DeliveryType;
import de.civento.eahtools.routingrepository.db.ResponsibilityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ServiceSearchObject extends SearchObject {
    private String leikaKey;
    private String civentoKey;
    private String name;
    private ResponsibilityType responsibilityType;
    private DeliveryType deliveryType;
}
