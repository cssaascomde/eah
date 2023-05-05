package de.civento.eahtools.routingrepository.db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AreaEntityRepositoryTest {

    @Autowired
    private AreaEntityRepository repository;

    @BeforeEach @AfterEach
    void cleanUp() {
        this.repository.deleteAll();
    }

    @Test
    void findByAgs() {
        init();
        AreaEntity areaEntity = this.repository.findByAgs("06636003");
        assertNotNull(areaEntity);
        assertEquals("Eschwege", areaEntity.getName());
        areaEntity = this.repository.findByAgs("06999999");
        assertNull(areaEntity);
    }


    @Test
    void findByQueryInAgsOrNameAndOverloadingOrderByNameAsc() {
        init();

        assertEquals(2,
                this.repository.findByQueryInAgsOrNameAndOverloadingOrderByNameAsc(
                        "066", null
                ).size());
        assertEquals(1,
                this.repository.findByQueryInAgsOrNameAndOverloadingOrderByNameAsc(
                        "066", false
                ).size());
        assertEquals(1,
                this.repository.findByQueryInAgsOrNameAndOverloadingOrderByNameAsc(
                        "066", true
                ).size());
        assertEquals(2,
                this.repository.findByQueryInAgsOrNameAndOverloadingOrderByNameAsc(
                        "06", true
                ).size());
        assertEquals(3,
                this.repository.findByQueryInAgsOrNameAndOverloadingOrderByNameAsc(
                        "06", false
                ).size());
        assertEquals(1,
                this.repository.findByQueryInAgsOrNameAndOverloadingOrderByNameAsc(
                        "Eschwege", null
                ).size());
        assertEquals(1,
                this.repository.findByQueryInAgsOrNameAndOverloadingOrderByNameAsc(
                        "Esch", null
                ).size());
        assertEquals(1,
                this.repository.findByQueryInAgsOrNameAndOverloadingOrderByNameAsc(
                        "wege", null
                ).size());
        assertEquals(1,
                this.repository.findByQueryInAgsOrNameAndOverloadingOrderByNameAsc(
                        "wege", true
                ).size());
        assertEquals(0,
                this.repository.findByQueryInAgsOrNameAndOverloadingOrderByNameAsc(
                        "wege", false
                ).size());
    }

    private void init() {
        this.repository.save(create("06636003", "Eschwege", true));
        this.repository.save(create("06531005", "Gießen", false));
        this.repository.save(create("06412000", "Frankfurt", true));
        this.repository.save(create("06411000", "Darmstadt", false));
        this.repository.save(create("06611000", "Kassel", false));
    }

    private AreaEntity create(String ags, String name, boolean overloading) {
        AreaEntity areaEntity = new AreaEntity();
        areaEntity.setAgs(ags);
        areaEntity.setName(name);
        areaEntity.setOverloading(overloading);
        return areaEntity;
    }
}