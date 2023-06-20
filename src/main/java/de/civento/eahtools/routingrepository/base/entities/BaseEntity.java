package de.civento.eahtools.routingrepository.base.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@MappedSuperclass
@EntityListeners(EntityListener.class)
public class BaseEntity {
    @NotNull(message = "Id darf nicht leer sein")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @Getter @Setter
    private String id;

    @Version
    @Column(name = "sys_version")
    @Getter @Setter
    private Integer sysVersion;

    @Column(name = "sys_modified_by", nullable = false)
    @Getter @Setter
    private String sysModifiedBy;

    @Column(name = "sys_modified_at", nullable = false)
    @Getter @Setter
    private Date sysModifiedAt;
}
