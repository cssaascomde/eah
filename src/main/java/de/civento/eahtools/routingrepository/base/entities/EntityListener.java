package de.civento.eahtools.routingrepository.base.entities;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class EntityListener {

    @PrePersist
    @PreUpdate
    public void updateSysFields(final BaseEntity entity) {
        // set modified by via spring security context
        String currentUsername = "Anonymous";

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            if (!(authentication instanceof AnonymousAuthenticationToken)) {
//                currentUsername = authentication.getName();
//            }
//        }
//
        entity.setSysModifiedBy(currentUsername);

        // set modified at
        entity.setSysModifiedAt(new Date());
    }
}