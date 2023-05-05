package de.civento.eahtools.routingrepository.db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServiceEntityRepositoryTest {

    @Autowired
    private ServiceEntityRepository repository;

    @BeforeEach
    @AfterEach
    void cleanUp() {
        this.repository.deleteAll();
    }

    @Test
    void findByLeikaId() {
        init();

        ServiceEntity serviceEntity = this.repository.findByLeikaId("99000001");
        assertNotNull(serviceEntity);
        assertEquals("Testleitung 01", serviceEntity.getName());
        serviceEntity = this.repository.findByLeikaId("99999999");
        assertNull(serviceEntity);
    }

    @Test
    void findByQueryInLeikaIdOrNameAndOverloadingOrderByNameAsc() {
        init();

        assertEquals(5,
                this.repository.findByQueryInLeikaIdOrNameAndOverloadingOrderByNameAsc(
                        "99", null
                ).size());
        assertEquals(3,
                this.repository.findByQueryInLeikaIdOrNameAndOverloadingOrderByNameAsc(
                        "99", false
                ).size());
        assertEquals(2,
                this.repository.findByQueryInLeikaIdOrNameAndOverloadingOrderByNameAsc(
                        "99", true
                ).size());
        assertEquals(5,
                this.repository.findByQueryInLeikaIdOrNameAndOverloadingOrderByNameAsc(
                        "Testleitung", null
                ).size());
        assertEquals(3,
                this.repository.findByQueryInLeikaIdOrNameAndOverloadingOrderByNameAsc(
                        "Testleitung", false
                ).size());
        assertEquals(2,
                this.repository.findByQueryInLeikaIdOrNameAndOverloadingOrderByNameAsc(
                        "Testleitung", true
                ).size());
        assertEquals(1,
                this.repository.findByQueryInLeikaIdOrNameAndOverloadingOrderByNameAsc(
                        "05", null
                ).size());
        assertEquals(0,
                this.repository.findByQueryInLeikaIdOrNameAndOverloadingOrderByNameAsc(
                        "05", false
                ).size());
        assertEquals(1,
                this.repository.findByQueryInLeikaIdOrNameAndOverloadingOrderByNameAsc(
                        "05", true
                ).size());

    }

    private void init() {
        this.repository.save(create("99000001", "Testleitung 01", false));
        this.repository.save(create("99000002", "Testleitung 02", false));
        this.repository.save(create("99000003", "Testleitung 03", false));
        this.repository.save(create("99000004", "Testleitung 04", true));
        this.repository.save(create("99000005", "Testleitung 05", true));
    }

    private ServiceEntity create(String leikaId, String name, boolean overloading) {
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setLeikaId(leikaId);
        serviceEntity.setName(name);
        serviceEntity.setOverloading(overloading);
        return serviceEntity;
    }
}