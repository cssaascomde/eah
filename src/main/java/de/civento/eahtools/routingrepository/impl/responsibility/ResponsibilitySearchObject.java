package de.civento.eahtools.routingrepository.impl.responsibility;

import de.civento.eahtools.routingrepository.base.businessobjects.SearchObject;
import de.civento.eahtools.routingrepository.db.DeliveryType;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponsibilitySearchObject extends SearchObject {
    private String ouId;
    private String serviceId;
    private String regionalKey;
    private DeliveryType deliveryType;
}
