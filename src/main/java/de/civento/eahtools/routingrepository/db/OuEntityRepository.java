package de.civento.eahtools.routingrepository.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.util.Optional;

public interface OuEntityRepository extends JpaRepository<OuEntity, String> {
    @Query("select o from OuEntity o where o.regionalKey = :regionalKey")
    Optional<OuEntity> findByRegionalKey(@Param("regionalKey") String regionalKey);

    @Query("""
            select o from OuEntity o
            where (:civentoKey is null or :civentoKey = '' or upper(o.civentoKey) like upper(:civentoKey))
                and (:regionalKey is null or :regionalKey = '' or upper(o.regionalKey) like upper(:regionalKey)) 
                and (:name is null or :name = '' or upper(o.name) like upper(:name)) 
                and (:type is null or o.type = :type)
            order by o.name""")
    Page<OuEntity> search(@Param("civentoKey") @Nullable String civentoKey,
                          @Param("regionalKey") @Nullable String regionalKey,
                          @Param("name") @Nullable String name,
                          @Param("type") @Nullable OuType type, Pageable pageable);

}