package de.civento.eahtools.routingrepository.db;

import de.civento.eahtools.routingrepository.base.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "service", uniqueConstraints = {
        @UniqueConstraint(name = "uc_serviceentity_civento_key", columnNames = {"civento_key"})
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceEntity extends BaseEntity {

    @Column(name = "leika_key", length = 16)
    @Getter @Setter
    private String leikaKey;

    @Column(name = "civento_key", length = 128)
    @Getter @Setter
    private String civentoKey;

    @Column(name = "name", nullable = false, length = 512)
    @Getter @Setter
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "responsibility_type", nullable = false)
    @Getter @Setter
    private ResponsibilityType responsibilityType;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_type", nullable = false)
    @Getter @Setter
    private DeliveryType deliveryType = DeliveryType.internal;


}