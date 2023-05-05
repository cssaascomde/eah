package de.civento.eahtools.routingrepository.db.analysis;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GebietEntityRepository extends JpaRepository<GebietEntity, UUID> {
}