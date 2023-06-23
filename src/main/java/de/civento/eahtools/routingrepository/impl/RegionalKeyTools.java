package de.civento.eahtools.routingrepository.impl;


import org.apache.commons.lang3.StringUtils;

public class RegionalKeyTools {

    public static String getCompleteRegionalKey(String regionalKey) {
        return formatRegionalKeyPart(regionalKey, 12);
    }

    public static String getMunicipalKey(String regionalKey) {
        return formatRegionalKeyPart(regionalKey, 8);
    }

    public static String getCountyKey(String regionalKey) {
        return formatRegionalKeyPart(regionalKey, 5);
    }

    public static String getRegionalCouncilKey(String regionalKey) {
        return formatRegionalKeyPart(regionalKey, 2);
    }

    private static String formatRegionalKeyPart(String regionalKey, int length) {
        if (!StringUtils.isEmpty(regionalKey)) {
            if (regionalKey.length() < length)
                return StringUtils.rightPad(regionalKey, length, "0");
            else
                return StringUtils.left(regionalKey, length);
        } else
            throw new IllegalArgumentException("Der Wert der Variable 'regionalKey' darf nicht NULL sein");
    }


}
