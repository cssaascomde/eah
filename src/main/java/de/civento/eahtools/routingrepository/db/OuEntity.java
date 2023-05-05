package de.civento.eahtools.routingrepository.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ou")
public class OuEntity extends AppEntity {

    @Column(name = "civento_key", length = 128)
    @Getter @Setter
    private String civentoKey;

    @Column(name = "regional_key", length = 16)
    @Getter @Setter
    private String regionalKey;

    @Column(name = "name", nullable = false, length = 512)
    @Getter @Setter
    private String name;

    @Enumerated
    @Column(name = "type", nullable = false)
    @Getter @Setter
    private OuType type;


}