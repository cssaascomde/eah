package de.civento.eahtools;

import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class TestXZuFi {

    @Test
    void testGetLeistung() {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("https://restapi-v3-he.zfinder.de/pst/byLeikaKey/%s",
                "99050012104000");
        HttpHeaders headers = new HttpHeaders();
        headers.set("api_key", "253684d4-f677-4619-84a4-97dd2a47b743");
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<JsonNode> response
                = restTemplate.exchange(url, HttpMethod.GET, request, JsonNode.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        System.out.println(response.getBody());
        JsonNode results = response.getBody().get("results");
        if (results.size() == 1) {
            System.out.printf("Id: %s\n", results.get(0).get("object").get("id").asText());
            System.out.printf("Name: %s\n", results.get(0).get("object").get("name").asText());
        }
    }

    @Test
    void testGetArea() {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("https://restapi-v3-he.zfinder.de/area/find?ags=%s", "06636003");
        HttpHeaders headers = new HttpHeaders();
        headers.set("api_key", "253684d4-f677-4619-84a4-97dd2a47b743");
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<JsonNode> response
                = restTemplate.exchange(url, HttpMethod.GET, request, JsonNode.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        System.out.println(response.getBody());
        JsonNode results = response.getBody().get("results");
        if (results.size() == 1) {
            System.out.printf("Id: %s\n", results.get(0).get("object").get("id").asText());
            System.out.printf("Name: %s\n", results.get(0).get("object").get("name").asText());
            System.out.printf("Ags: %s\n", results.get(0).get("object").get("ags").asText());
            System.out.printf("Rs: %s\n", results.get(0).get("object").get("rs").asText());
        }
    }

    @Test
    void getZustaendigkeit() {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("https://restapi-v3-he.zfinder.de/ou/findByCompetence?areaId=%s&pstId=%s",
                "8957653", "8957937");
        HttpHeaders headers = new HttpHeaders();
        headers.set("api_key", "253684d4-f677-4619-84a4-97dd2a47b743");
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<JsonNode> response
                = restTemplate.exchange(url, HttpMethod.GET, request, JsonNode.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        System.out.println(response.getBody());
        JsonNode results = response.getBody().get("results");
        if (results.size() == 1) {
            System.out.printf("Id: %s\n", results.get(0).get("object").get("id").asText());
            System.out.printf("Name: %s\n", results.get(0).get("object").get("name").asText());
            JsonNode addresses = results.get(0).get("object").get("addresses");
            if (addresses.size() > 0) {
                System.out.printf("Strasse: %s\n", addresses.get(0).get("street").asText());
                System.out.printf("Hausnummer: %s\n", addresses.get(0).get("houseNumber").asText());
                System.out.printf("Stadt: %s\n", addresses.get(0).get("city").asText());
                System.out.printf("Postleitzahl: %s\n", addresses.get(0).get("zipcode").asText());
            }
        }
    }
}
