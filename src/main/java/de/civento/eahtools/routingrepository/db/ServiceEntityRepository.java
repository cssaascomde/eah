package de.civento.eahtools.routingrepository.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.util.Optional;

public interface ServiceEntityRepository extends JpaRepository<ServiceEntity, String> {

    @Query("select s from ServiceEntity s where upper(s.civentoKey) = upper(:civentoKey)")
    Optional<ServiceEntity> findByCiventoKeyIgnoreCase(@Param("civentoKey") String civentoKey);

    @Query("""
            select s from ServiceEntity s
            where (:leikaKey is null or :leikaKey = '' or s.leikaKey like :leikaKey)
            and (:civentoKey is null or :civentoKey = '' or upper(s.civentoKey) like upper(:civentoKey)) 
            and (:name is null or :name = '' or upper(s.name) like upper(:name)) 
            and (:responsibilityType is null or s.responsibilityType = :responsibilityType) 
            and (:deliveryType is null or s.deliveryType = :deliveryType)
            order by s.name""")
    Page<ServiceEntity> search(
            @Param("leikaKey") @Nullable String leikaKey,
            @Param("civentoKey") @Nullable String civentoKey,
            @Param("name") @Nullable String name,
            @Param("responsibilityType") @Nullable ResponsibilityType responsibilityType,
            @Param("deliveryType") @Nullable DeliveryType deliveryType,
            Pageable pageable);




}