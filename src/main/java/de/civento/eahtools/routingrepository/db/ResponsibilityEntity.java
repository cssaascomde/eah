package de.civento.eahtools.routingrepository.db;

import de.civento.eahtools.routingrepository.base.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "responsibility")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponsibilityEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "ou_entity_id")
    @Getter @Setter
    private OuEntity ouEntity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "service_entity_id", nullable = false)
    @Getter @Setter
    private ServiceEntity serviceEntity;

    @Column(name = "regional_key", length = 12, nullable = false)
    @Getter @Setter
    private String regionalKey;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_type", nullable = false)
    @Getter @Setter
    private DeliveryType deliveryType = DeliveryType.internal;

    @Enumerated(EnumType.STRING)
    @Column(name = "interface_type", length = 16)
    @Getter @Setter
    private InterfaceType interfaceType;


}