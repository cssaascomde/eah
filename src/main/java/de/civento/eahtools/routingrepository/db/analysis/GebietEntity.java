package de.civento.eahtools.routingrepository.db.analysis;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity(name = "analysis_gebiet")
public class GebietEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    @Getter @Setter
    private UUID id;

    @Column(name = "zufi_id", nullable = false)
    @Getter @Setter
    private String zufiId;

    @Column(name = "name", nullable = false)
    @Getter @Setter
    private String name;

    @Column(name = "ags")
    @Getter @Setter
    private String ags;

    @Column(name = "rs")
    @Getter @Setter
    private String rs;

    @Column(name = "zustaendigkeit")
    @Getter @Setter
    private String zustaendigkeit;
}
