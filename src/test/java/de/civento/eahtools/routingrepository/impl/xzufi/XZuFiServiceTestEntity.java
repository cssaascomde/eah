package de.civento.eahtools.routingrepository.impl.xzufi;

import de.civento.eahtools.routingrepository.impl.objects.Area;
import de.civento.eahtools.routingrepository.impl.objects.Ou;
import de.civento.eahtools.routingrepository.impl.objects.Service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class XZuFiServiceTestEntity {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void getAreaByAgs() {
        XZuFiService zuFiService = this.applicationContext.getBean(XZuFiService.class);

        // Abfrage ohne Ergebnis
        Assertions.assertNull(zuFiService.getAreaByAgs("00000000"));

        // Abfrage mit Ergebnis
        Area area = zuFiService.getAreaByAgs("06636003");
        Assertions.assertNotNull(area);
        Assertions.assertEquals("8957653", area.getId());
        Assertions.assertEquals("Eschwege, Kreisstadt", area.getName());
        Assertions.assertEquals("06636003", area.getAgs());
        Assertions.assertEquals("066360003003", area.getRs());

    }

    @Test
    void getServiceByLeikaId() {
        XZuFiService zuFiService = this.applicationContext.getBean(XZuFiService.class);

        // Abfrage ohne Ergebnis
        Assertions.assertNull(zuFiService.getServiceByLeikaId("00000000000000"));

        // Abfrage mit Ergebnis
        Service service = zuFiService.getServiceByLeikaId("99050012104000");
        Assertions.assertNotNull(service);
        Assertions.assertEquals("8957937", service.getId());
        Assertions.assertEquals("Gewerbe anmelden", service.getName());
        Assertions.assertEquals("99050012104000", service.getLeikaId());
    }

    @Test
    void getOuByServiceIdAndAreaId() {
        XZuFiService zuFiService = this.applicationContext.getBean(XZuFiService.class);

        // Abfrage ohne Ergebnis
        Assertions.assertNull(zuFiService.getOuByServiceIdAndAreaId("0000000", "0000000"));

        // Abfrage mit Ergebnis
        Ou ou = zuFiService.getOuByServiceIdAndAreaId("8957937", "8957653");
        Assertions.assertNotNull(ou);
        Assertions.assertEquals("9555198", ou.getId());
        Assertions.assertEquals("Magistrat der Kreisstadt Eschwege - Geschäftsbereich 2 - Bürgerdienstleistungen und Ordnungswesen",
                ou.getName());
        Assertions.assertEquals("Obermarkt", ou.getStreet());
        Assertions.assertEquals("22", ou.getHousenumber());
        Assertions.assertEquals("Eschwege, Kreisstadt", ou.getCity());
        Assertions.assertEquals("37269", ou.getZipCode());
    }
}