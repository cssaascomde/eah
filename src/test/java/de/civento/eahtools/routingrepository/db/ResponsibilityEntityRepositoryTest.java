package de.civento.eahtools.routingrepository.db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
class ResponsibilityEntityRepositoryTest {
    @Autowired ResponsibilityEntityRepository repository;
    @Autowired ServiceEntityRepository serviceEntityRepository;
    @Autowired OuEntityRepository ouEntityRepository;

    @BeforeEach
    @AfterEach
    void cleanup() {
        this.repository.deleteAll();
        this.ouEntityRepository.deleteAll();
        this.serviceEntityRepository.deleteAll();
    }

    @Test
    void test() {
        ServiceEntity service = this.serviceEntityRepository.save(
                ServiceEntity.builder().name("Test").civentoKey("civ1")
                        .deliveryType(DeliveryType.internal)
                        .responsibilityType(ResponsibilityType.regional_council).build());
        OuEntity ou1 = this.ouEntityRepository.save(
                OuEntity.builder().name("Test").civentoKey("00.00.99")
                        .type(OuType.other).build());
        OuEntity ou2 = this.ouEntityRepository.save(
                OuEntity.builder().name("Test2").civentoKey("00.00.98")
                        .type(OuType.other).build());
        repository.save(ResponsibilityEntity.builder().ouEntity(ou1).serviceEntity(service)
                .deliveryType(DeliveryType.internal).build());
        repository.save(ResponsibilityEntity.builder().ouEntity(ou2).serviceEntity(service)
                .deliveryType(DeliveryType.internal).build());
        repository.search(null, null, null, Pageable.ofSize(10));
        repository.search(null, null, null, Pageable.ofSize(10));
    }
}