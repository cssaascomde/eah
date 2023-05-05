package de.civento.eahtools.routingrepository.impl.xzufi;

import com.fasterxml.jackson.databind.JsonNode;
import de.civento.eahtools.routingrepository.impl.objects.Area;
import de.civento.eahtools.routingrepository.impl.objects.Ou;
import de.civento.eahtools.routingrepository.impl.objects.Service;
import lombok.extern.java.Log;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Die Klasse kapselt die Funktionen zum Aufruf des Zuständigkeitsfinders
 */
@Component
@Log
public class XZuFiService {

    private final Environment environment;

    public XZuFiService(Environment environment) {
        this.environment = environment;
    }

    /**
     * Die Funktion ermittelt das {@link Area} Objekt auf Basis des AGS
     * @param ags Der allgemeine Gemeindeschlüssel
     * @return Das gefundene {@link Area} Objekt oder NULL
     */
    public Area getAreaByAgs(String ags) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s/area/find?ags=%s", getBaseUrl(), ags);

        ResponseEntity<JsonNode> response
                = restTemplate.exchange(url, HttpMethod.GET, getRequestEntity(), JsonNode.class);

        if (response.getBody() == null)
            throw new RuntimeException("Undefinierte XZuFi-Antwort: " + response.getBody());

        JsonNode results = response.getBody().get("results");
        if (results.size() == 1) {
            // Mapping für das Area-Objekt
            Area area = new Area();
            area.setId(getAsText(results.get(0).get("object").get("id")));
            area.setName(getAsText(results.get(0).get("object").get("name")));
            area.setAgs(getAsText(results.get(0).get("object").get("ags")));
            area.setRs(getAsText(results.get(0).get("object").get("rs")));

            // Log-Eintrag
            log.info(String.format(".getAreaByAgs: Suchparameter %s | Ergebnis %s", ags, area));

            return area;
        } else {
            // Log-Eintrag
            log.warning(String.format(".getAreaByAgs: Suchparameter %s | Kein Ergebnis gefunden", ags));

            return null;
        }
    }

    /**
     * Die Funktion ermittelt das {@link Service} Objekt auf Basis der Leika-ID
     * @param leikaId Die ID aus der Leistungskatalog
     * @return Das gefundene {@link Service} Objekt oder NULL
     */
    public Service getServiceByLeikaId(String leikaId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s/pst/byLeikaKey/%s", getBaseUrl(), leikaId);

        ResponseEntity<JsonNode> response
                = restTemplate.exchange(url, HttpMethod.GET, getRequestEntity(), JsonNode.class);

        if (response.getBody() == null)
            throw new RuntimeException("Undefinierte XZuFi-Antwort: " + response.getBody());

        JsonNode results = response.getBody().get("results");
        if (results.size() == 1) {
            Service service = new Service();
            service.setId(getAsText(results.get(0).get("object").get("id")));
            service.setLeikaId(leikaId);
            service.setName(getAsText(results.get(0).get("object").get("name")));

            // Log-Eintrag
            log.info(String.format(".getServiceByLeikaId: Suchparameter %s | Ergebnis %s", leikaId, service));

            return service;
        } else {
            // Log-Eintrag
            log.warning(String.format(".getServiceByLeikaId: Suchparameter %s | Kein Ergebnis gefunden", leikaId));

            return null;
        }
    }

    /**
     * Die Funktion ermittelt die zuständige {@link Ou} auf Basis der AreaId und der ServiceId
     * @param serviceId die Id des Services
     * @param areaId die Id der Area
     * @return Die gefundene {@link Ou} oder NULL
     */
    public Ou getOuByServiceIdAndAreaId(String serviceId, String areaId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%sou/findByCompetence?areaId=%s&pstId=%s", getBaseUrl(), areaId, serviceId);

        ResponseEntity<JsonNode> response
                = restTemplate.exchange(url, HttpMethod.GET, getRequestEntity(), JsonNode.class);
        if (response.getBody() == null)
            throw new RuntimeException("Undefinierte XZuFi-Antwort: " + response.getBody());

        JsonNode results = response.getBody().get("results");
        if (results.size() == 1) {
            Ou ou = new Ou();

            ou.setId(getAsText(results.get(0).get("object").get("id")));
            ou.setName(getAsText(results.get(0).get("object").get("name")));
            JsonNode addresses = results.get(0).get("object").get("addresses");
            if (addresses.size() > 0) {
                ou.setStreet(getAsText(addresses.get(0).get("street")));
                ou.setHousenumber(getAsText(addresses.get(0).get("houseNumber")));
                ou.setCity(getAsText(addresses.get(0).get("city")));
                ou.setZipCode(getAsText(addresses.get(0).get("zipcode")));
            }

            // Log-Eintrag
            log.info(String.format(".getOuByServiceIdAndAreaId: Suchparameter %s (service), %s (area) | Ergebnis %s",
                    serviceId, areaId, ou));

            return ou;
        } else {
            log.warning(String.format(".getOuByServiceIdAndAreaId: Suchparameter %s (service), %s (area) | Kein Ergebnis gefunden",
                    serviceId, areaId));

            return null;
        }
    }

    private String getAsText(JsonNode jsonNode) {
        if (jsonNode != null)
            return jsonNode.asText();
        else
            return "";
    }

    private String getBaseUrl() {
        return this.environment.getProperty("xzufi.url");
    }

    private HttpEntity<?> getRequestEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("api_key", this.environment.getProperty("xzufi.api-key"));
        return new HttpEntity<>(headers);
    }
}
