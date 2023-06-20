package de.civento.eahtools.routingrepository.impl.responsibility;

import de.civento.eahtools.routingrepository.base.businessobjects.BusinessObject;
import de.civento.eahtools.routingrepository.db.DeliveryType;
import de.civento.eahtools.routingrepository.impl.ou.Ou;
import de.civento.eahtools.routingrepository.impl.service.Service;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Responsibility extends BusinessObject {
    private Ou ou;
    private Service service;
    private DeliveryType deliveryType = DeliveryType.internal;
}
