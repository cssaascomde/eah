package de.civento.eahtools.routingrepository.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ServiceEntityRepository extends JpaRepository<ServiceEntity, UUID> {
    @Query("""
            select s from ServiceEntity s
            where upper(s.civentoKey) like upper(concat('%', :civentoKey, '%'))
            order by s.name""")
    Page<ServiceEntity> findByCiventoKeyContainsIgnoreCaseAllIgnoreCaseOrderByNameAsc(
            @Param("civentoKey") String civentoKey, Pageable pageable);

    @Query("select s from ServiceEntity s where upper(s.civentoKey) = upper(:civentoKey)")
    Optional<ServiceEntity> findByCiventoKeyAllIgnoreCase(@Param("civentoKey") String civentoKey);

}