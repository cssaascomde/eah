package de.civento.eahtools.routingrepository.db;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@EntityListeners(EntityListener.class)
public abstract class AppEntity implements IEntity {

    @Id
    @Column(name = "id")
    @Getter @Setter
    private UUID id;

    @Column(name = "modified_at")
    @Getter @Setter
    private Date modifiedAt;

    @Size(max = 256)
    @Column(name = "sys_modified_by")
    @NotNull
    @Getter @Setter
    private String sysModifiedBy;

    @Override
    public UUID getIdentifier() {
        return getId();
    }

    @Override
    public void setIdentifier(UUID identifier) {
        setId(identifier);
    }

    @Override
    public void setUpdatedByUser(String username) {
        setSysModifiedBy(username);
    }
}