package de.civento.eahtools.routingrepository.impl.responsibility;

import de.civento.eahtools.routingrepository.base.businessobjects.SearchObject;
import de.civento.eahtools.routingrepository.db.DeliveryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ResponsibilitySearchObject extends SearchObject {
    private String ouId;
    private String serviceId;
    private DeliveryType deliveryType;
}
