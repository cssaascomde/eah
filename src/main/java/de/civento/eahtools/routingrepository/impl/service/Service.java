package de.civento.eahtools.routingrepository.impl.service;

import de.civento.eahtools.routingrepository.base.businessobjects.BusinessObject;
import de.civento.eahtools.routingrepository.db.DeliveryType;
import de.civento.eahtools.routingrepository.db.ResponsibilityType;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Service extends BusinessObject {
    private String leikaKey;
    private String civentoKey;
    private String name;
    private ResponsibilityType responsibilityType;
    @Builder.Default
    private DeliveryType deliveryType = DeliveryType.internal;
}
