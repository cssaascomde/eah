package de.civento.eahtools.routingrepository.impl.responsibility;

import de.civento.eahtools.routingrepository.base.businessobjects.BusinessObject;
import de.civento.eahtools.routingrepository.db.DeliveryType;
import de.civento.eahtools.routingrepository.impl.ou.Ou;
import de.civento.eahtools.routingrepository.impl.service.Service;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Responsibility extends BusinessObject {
    private Ou ou;
    private Service service;
    @Builder.Default
    private DeliveryType deliveryType = DeliveryType.internal;
}
