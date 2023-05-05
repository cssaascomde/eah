package de.civento.eahtools.routingrepository.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class ResponsibilityServiceTestEntity {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void get() {
        ResponsibilityResponse response;
        ResponsibilityService service = applicationContext.getBean(ResponsibilityService.class);
        response = service.get(getRequest("00000000", "00000000000000"));
        Assertions.assertNotNull(response);
        Assertions.assertNull(response.getService());
        Assertions.assertNull(response.getArea());
        Assertions.assertNull(response.getOu());

        response = service.get(getRequest("06636003", "00000000000000"));
        Assertions.assertNotNull(response);
        Assertions.assertNull(response.getService());
        Assertions.assertNotNull(response.getArea());
        Assertions.assertNull(response.getOu());

        response = service.get(getRequest("00000000", "99050012104000"));
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getService());
        Assertions.assertNull(response.getArea());
        Assertions.assertNull(response.getOu());

        response = service.get(getRequest("06636003", "99050012104000"));
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getService());
        Assertions.assertNotNull(response.getArea());
        Assertions.assertNotNull(response.getOu());
    }

    private ResponsibilityRequest getRequest(String ags, String leikaId) {
        ResponsibilityRequest request = new ResponsibilityRequest();
        request.setAgs(ags);
        request.setLeikaId(leikaId);
        return request;
    }
}