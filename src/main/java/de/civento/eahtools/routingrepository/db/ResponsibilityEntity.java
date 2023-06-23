package de.civento.eahtools.routingrepository.db;

import de.civento.eahtools.routingrepository.base.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "responsibility", uniqueConstraints = {
        @UniqueConstraint(name = "uc_responsibilityentity", columnNames = {"ou_entity_id", "service_entity_id"})
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponsibilityEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "ou_entity_id")
    private OuEntity ouEntity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "service_entity_id", nullable = false)
    @Getter @Setter
    private ServiceEntity serviceEntity;

    @Builder.Default
    @Enumerated(EnumType.STRING)
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