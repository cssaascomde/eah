package de.civento.eahtools.routingrepository.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "service")
public class ServiceEntity extends AppEntity {

    @Column(name = "leika_key", length = 16)
    @Getter @Setter
    private String leikaKey;

    @Column(name = "civento_key", length = 128)
    @Getter @Setter
    private String civentoKey;

    @Column(name = "name", nullable = false, length = 512)
    @Getter @Setter
    private String name;

    @Enumerated
    @Column(name = "responsibility_type", nullable = false)
    @Getter @Setter
    private ResponsibilityType responsibilityType;

    @Enumerated
    @Column(name = "delivery_type", nullable = false)
    @Getter @Setter
    private DeliveryType deliveryType = DeliveryType.internal;


}