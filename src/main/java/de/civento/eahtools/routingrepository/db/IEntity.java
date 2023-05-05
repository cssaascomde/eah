package de.civento.eahtools.routingrepository.db;

import java.util.UUID;

public interface IEntity {

    UUID getIdentifier();
    void setIdentifier(UUID identifier);
    void setUpdatedByUser(String username);
}
