package de.civento.eahtools.routingrepository.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "responsibility")
public class ResponsibilityEntity extends AppEntity {
    @ManyToOne
    @JoinColumn(name = "ou_entity_id")
    private OuEntity ouEntity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "service_entity_id", nullable = false)
    @Getter @Setter
    private ServiceEntity serviceEntity;

    @Enumerated
    @Column(name = "delivery_type", nullable = false)
    @Getter @Setter
    private DeliveryType deliveryType = DeliveryType.internal;

    public OuEntity getOuEntity() {
        return ouEntity;
    }

    public void setOuEntity(OuEntity ouEntity) {
        this.ouEntity = ouEntity;
    }


}