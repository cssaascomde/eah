package de.civento.eahtools.routingrepository.db;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class EntityListener {

    @PrePersist
    @PreUpdate
    public void pre(final @NotNull AppEntity entity) {
        // set UUID, when empty
        if (entity.getIdentifier() == null)
            entity.setIdentifier(UUID.randomUUID());

        // set modified by via spring security context
        String currentUsername = "Anonymous";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
                currentUsername = authentication.getName();
        }
        entity.setUpdatedByUser(currentUsername);
    }
}