package de.civento.eahtools.routingrepository.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Optional;

public interface ResponsibilityEntityRepository extends JpaRepository<ResponsibilityEntity, String> {

    @Query("""
            select r from ResponsibilityEntity r
            where upper(r.ouEntity.regionalKey) = upper(:regionalKey)
            and upper(r.serviceEntity.civentoKey) = upper(:civentoKey)""")
    Optional<ResponsibilityEntity> findByOuRegionalKeyAndServiceCiventoKey(
            @Param("regionalKey") @NonNull String regionalKey,
            @Param("civentoKey") @NonNull String civentoKey);

    @Query("""
            select r from ResponsibilityEntity r
            where (:ou_entity_id is null or :ou_entity_id = '' or r.ouEntity.id = :ou_entity_id)
            and (:service_entity_id is null or :service_entity_id = '' or r.serviceEntity.id = :service_entity_id)
            and (:deliveryType is null or r.deliveryType = :deliveryType)
            order by r.serviceEntity.name, r.ouEntity.name""")
    Page<ResponsibilityEntity> search(
            @Param("ou_entity_id") @Nullable String ouEntityId,
            @Param("service_entity_id") @Nullable String serviceEntityId,
            @Param("deliveryType") @Nullable DeliveryType deliveryType,
            Pageable pageable);



}