package de.civento.eahtools.routingrepository.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class RegionalKeyToolsTest {

    @Test
    void testWithLength15() {
        String regionalKey = "123456789012345";
        Assertions.assertEquals(RegionalKeyTools.getCompleteRegionalKey(regionalKey), "123456789012");
        Assertions.assertEquals(RegionalKeyTools.getMunicipalKey(regionalKey), "12345678");
        Assertions.assertEquals(RegionalKeyTools.getCountyKey(regionalKey), "12345");
        Assertions.assertEquals(RegionalKeyTools.getRegionalCouncilKey(regionalKey), "12");
    }
    @Test
    void testWithLength12() {
        String regionalKey = "123456789012";
        Assertions.assertEquals(RegionalKeyTools.getCompleteRegionalKey(regionalKey), "123456789012");
        Assertions.assertEquals(RegionalKeyTools.getMunicipalKey(regionalKey), "12345678");
        Assertions.assertEquals(RegionalKeyTools.getCountyKey(regionalKey), "12345");
        Assertions.assertEquals(RegionalKeyTools.getRegionalCouncilKey(regionalKey), "12");
    }

    @Test
    void testWithLength8() {
        String regionalKey = "12345678";
        Assertions.assertEquals(RegionalKeyTools.getCompleteRegionalKey(regionalKey), "123456780000");
        Assertions.assertEquals(RegionalKeyTools.getMunicipalKey(regionalKey), "12345678");
        Assertions.assertEquals(RegionalKeyTools.getCountyKey(regionalKey), "12345");
        Assertions.assertEquals(RegionalKeyTools.getRegionalCouncilKey(regionalKey), "12");
    }
    @Test
    void testWithLength5() {
        String regionalKey = "12345";
        Assertions.assertEquals(RegionalKeyTools.getCompleteRegionalKey(regionalKey), "123450000000");
        Assertions.assertEquals(RegionalKeyTools.getMunicipalKey(regionalKey), "12345000");
        Assertions.assertEquals(RegionalKeyTools.getCountyKey(regionalKey), "12345");
        Assertions.assertEquals(RegionalKeyTools.getRegionalCouncilKey(regionalKey), "12");
    }
    @Test
    void testWithLength2() {
        String regionalKey = "12";
        Assertions.assertEquals(RegionalKeyTools.getCompleteRegionalKey(regionalKey), "120000000000");
        Assertions.assertEquals(RegionalKeyTools.getMunicipalKey(regionalKey), "12000000");
        Assertions.assertEquals(RegionalKeyTools.getCountyKey(regionalKey), "12000");
        Assertions.assertEquals(RegionalKeyTools.getRegionalCouncilKey(regionalKey), "12");
    }

    void testWithLengthNULL() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                RegionalKeyTools.getCompleteRegionalKey(null);
            }
        });
    }

}