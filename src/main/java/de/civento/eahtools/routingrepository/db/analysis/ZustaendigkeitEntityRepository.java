package de.civento.eahtools.routingrepository.db.analysis;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ZustaendigkeitEntityRepository extends JpaRepository<ZustaendigkeitEntity, UUID> {
    List<ZustaendigkeitEntity> findByAgsAndLeikaId(String ags, String leikaId);

}