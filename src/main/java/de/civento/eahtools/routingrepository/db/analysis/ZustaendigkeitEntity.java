package de.civento.eahtools.routingrepository.db.analysis;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity(name = "analysis_zustaendigkeit")
public class ZustaendigkeitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    @Getter @Setter
    private UUID id;

    @Column(name = "leika_id")
    @Getter @Setter
    private String leikaId;

    @Column(name = "ags")
    @Getter @Setter
    private String ags;

    @Column(name = "name")
    @Getter @Setter
    private String name;

    @Column(name = "zufi_id")
    @Getter @Setter
    private String zufiId;
}
