package de.civento.eahtools.routingrepository.impl.responsibility;

import de.civento.eahtools.routingrepository.db.*;
import de.civento.eahtools.routingrepository.impl.ou.Ou;
import de.civento.eahtools.routingrepository.impl.ou.OuService;
import de.civento.eahtools.routingrepository.impl.service.Service;
import de.civento.eahtools.routingrepository.impl.service.ServiceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResponsibilityServiceTest {
    private Ou kommune, landkreis, rp, zentral;
    private Service service1, service2, service3, service4;
    @Autowired private ResponsibilityService service;
    @Autowired private OuService ouService;
    @Autowired private ServiceService serviceService;
    @Autowired private ResponsibilityEntityRepository responsibilityEntityRepository;
    @Autowired private OuEntityRepository ouEntityRepository;
    @Autowired private ServiceEntityRepository serviceEntityRepository;

    @BeforeEach
    @AfterEach
    void cleanup() {
        this.responsibilityEntityRepository.deleteAll();
        this.ouEntityRepository.deleteAll();
        this.serviceEntityRepository.deleteAll();
    }

    @Test
    void getRepository() {
        assertNotNull(service.getRepository());
    }

    @Test
    void getSimpleClassName() {
        assertEquals(Responsibility.class.getSimpleName(), service.getBusinessObjectName());
    }

    @Test
    void create() {
        createDefaultData();
        this.service.create(Responsibility.builder().ou(kommune).service(service1).build());
        this.service.create(Responsibility.builder().ou(kommune).service(service2).build());
        this.service.create(Responsibility.builder().ou(kommune).service(service3).build());

        assertEquals(3, this.service.search(new ResponsibilitySearchObject()).getTotalElements());
    }

    @Test
    void update() {
        createDefaultData();
        Responsibility responsibility = this.service.create(
                Responsibility.builder().ou(kommune).service(service1).build());
        responsibility.setOu(landkreis);
        Responsibility updated = this.service.update(responsibility);
        assertEquals("Landkreis", updated.getOu().getName());
    }

    @Test
    void search() {
        createDefaultData();
        this.service.create(Responsibility.builder().ou(kommune).service(service1).build());
        this.service.create(Responsibility.builder().ou(kommune).service(service2).build());
        this.service.create(Responsibility.builder().ou(kommune).service(service3).build());

        assertEquals(
                3, this.service.search(
                        ResponsibilitySearchObject.builder().build()
                ).getTotalElements()
        );
        assertEquals(
                3, this.service.search(
                        ResponsibilitySearchObject.builder().ouId(kommune.getId()).build()
                ).getTotalElements()
        );
        assertEquals(
                3, this.service.search(
                        ResponsibilitySearchObject.builder().ouId(kommune.getId()).deliveryType(DeliveryType.internal)
                                .build()
                ).getTotalElements()
        );
        assertEquals(
                1, this.service.search(
                        ResponsibilitySearchObject.builder().ouId(kommune.getId()).deliveryType(DeliveryType.internal)
                                .serviceId(service1.getId()).build()
                ).getTotalElements()
        );
        assertEquals(
                0, this.service.search(
                        ResponsibilitySearchObject.builder().ouId(kommune.getId()).deliveryType(DeliveryType.internal)
                                .serviceId(UUID.randomUUID().toString()).build()
                ).getTotalElements()
        );
        assertEquals(
                3, this.service.search(
                        ResponsibilitySearchObject.builder().deliveryType(DeliveryType.internal).build()
                ).getTotalElements()
        );
        assertEquals(
                1, this.service.search(
                        ResponsibilitySearchObject.builder().serviceId(service2.getId()).build()
                ).getTotalElements()
        );
        assertEquals(
                0, this.service.search(
                        ResponsibilitySearchObject.builder().serviceId(UUID.randomUUID().toString()).build()
                ).getTotalElements()
        );
    }

    @Test
    void processLookupRequest() {
        createDefaultData();
        this.service.create(Responsibility.builder().ou(kommune).service(service1).build());
        this.service.create(Responsibility.builder().ou(landkreis).service(service2).build());
        this.service.create(Responsibility.builder().ou(rp).service(service3).build());
        this.service.create(Responsibility.builder().ou(zentral).service(service4).build());

        Responsibility responsibility;
        responsibility = this.service.processLookupRequest(ResponsibilityLookupRequest.builder()
                        .ouRegionalKey("06999999").serviceCiventoKey("civ1")
                .build()).getResponsibility();
        assertEquals("00.00.EAH.ZS.01", responsibility.getOu().getCiventoKey());

        responsibility = this.service.processLookupRequest(ResponsibilityLookupRequest.builder()
                .ouRegionalKey("06999999").serviceCiventoKey("civ2")
                .build()).getResponsibility();
        assertEquals("00.00.EAH.ZS.02", responsibility.getOu().getCiventoKey());

        responsibility = this.service.processLookupRequest(ResponsibilityLookupRequest.builder()
                .ouRegionalKey("06999999").serviceCiventoKey("civ3")
                .build()).getResponsibility();
        assertEquals("00.00.EAH.ZS.03", responsibility.getOu().getCiventoKey());

        responsibility = this.service.processLookupRequest(ResponsibilityLookupRequest.builder()
                .ouRegionalKey("06999999").serviceCiventoKey("civ4")
                .build()).getResponsibility();
        assertEquals("00.00.EAH.ZS.04", responsibility.getOu().getCiventoKey());

        responsibility = this.service.processLookupRequest(ResponsibilityLookupRequest.builder()
                .ouRegionalKey("06999999").serviceCiventoKey("civ5")
                .build()).getResponsibility();
        assertEquals("00.00.EAH.ZS.9999", responsibility.getOu().getCiventoKey());
    }

    private void createDefaultData() {
        kommune = this.ouService.create(Ou.builder()
                .name("Kommune")
                .civentoKey("00.00.EAH.ZS.01")
                .regionalKey("06999999")
                .type(OuType.municipal).build());
        landkreis = this.ouService.create(Ou.builder()
                .name("Landkreis")
                .civentoKey("00.00.EAH.ZS.02")
                .regionalKey("06999")
                .type(OuType.county).build());
        rp = this.ouService.create(Ou.builder()
                .name("RP")
                .civentoKey("00.00.EAH.ZS.03")
                .regionalKey("06")
                .type(OuType.regional_council).build());
        zentral = this.ouService.create(Ou.builder()
                .name("Zentral")
                .civentoKey("00.00.EAH.ZS.04")
                .regionalKey("0")
                .type(OuType.other).build());

        service1 = this.serviceService.create(Service.builder()
                .name("service1")
                .leikaKey("l1")
                .civentoKey("civ1")
                .responsibilityType(ResponsibilityType.unknown)
                .build());

        service2 = this.serviceService.create(Service.builder()
                .name("service2")
                .leikaKey("l2")
                .civentoKey("civ2")
                .responsibilityType(ResponsibilityType.unknown)
                .build());

        service3 = this.serviceService.create(Service.builder()
                .name("service3")
                .leikaKey("l3")
                .civentoKey("civ3")
                .responsibilityType(ResponsibilityType.unknown)
                .build());
        service4 = this.serviceService.create(Service.builder()
                .name("service4")
                .leikaKey("l4")
                .civentoKey("civ4")
                .responsibilityType(ResponsibilityType.unknown)
                .build());
    }
}