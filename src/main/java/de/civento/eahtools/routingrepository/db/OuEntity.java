package de.civento.eahtools.routingrepository.db;

import de.civento.eahtools.routingrepository.base.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ou", indexes = {
        @Index(name = "idx_ouentity_regional_key", columnList = "regional_key")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uc_ouentity_civento_key", columnNames = {"civento_key"})
})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OuEntity extends BaseEntity {

    @Column(name = "civento_key", length = 128)
    @Getter @Setter
    private String civentoKey;

    @Column(name = "regional_key", length = 12)
    @Getter @Setter
    private String regionalKey;

    @Column(name = "name", nullable = false, length = 512)
    @Getter @Setter
    private String name;

    @Column(name = "address", length = 512)
    @Getter @Setter
    private String address;

    @Column(name = "zip_code", length = 5)
    @Getter @Setter
    private String zipCode;

    @Column(name = "city", length = 128)
    @Getter @Setter
    private String city;

    @Column(name = "email", length = 256)
    @Getter @Setter
    private String email;

    @Column(name = "phone", length = 32)
    @Getter @Setter
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    @Getter @Setter
    private OuType type;


}